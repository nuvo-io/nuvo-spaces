package nuvo.spaces.remote

import nuvo.spaces.{RemoteSpaceLocator, SpaceLocator, Stream, Space}
import nuvo.core.{Tuple, Time, Duration}
import nuvo.concurrent.Worker
import nuvo.net.{NetLink, Locator}
import nuvo.nio.{LittleEndian, RawBuffer}
import nuvo.nio.prelude._
import nuvo.spaces.remote.streams._
import nuvo.runtime.Config._
import collection.mutable.ListBuffer


import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global

// spacename@remote:encoding/transport:ip:port

object SpaceProxy {
  def apply[T <: Tuple](sl: RemoteSpaceLocator) = new SpaceProxy[T](sl)
}
class SpaceProxy[T <: Tuple](val spaceLoc: RemoteSpaceLocator) extends Space[T] {

  // == Manages Streams from the remote space
  class StreamsManager(val locator: Locator, spaceHash: Int) {

    private val buf = allocator.allocateDirect(Networking.defaultBufferSize)
    buf.order(LittleEndian)

    private var streamProxyMap = Map[Int, StreamProxy[T]]()

    // == Represents a specific stream
    class StreamProxy[Q <: T](val link: NetLink, val streamHash: Int, val observer: Q => Unit) extends Stream[Q] {

      var observers: ListBuffer[(Q) => Unit] = new ListBuffer[(Q) => Unit]()
      observers += observer

      private val workerBuf = allocator.allocateDirect()
      buf.order(LittleEndian)

      val ioWorker = Worker runLoop {
        workerBuf.clear()
        val msg = readMessage[StreamTuple](link, workerBuf)
        observers.foreach(f => f(msg.t.asInstanceOf[Q]))
      }

      def close() {
        buf.clear()
        buf.putObject(CloseStream(streamHash))
        buf.flip()
        link.write(buf)
        buf.clear()
        ioWorker.interrupt()
        link.channel.close()
      }
    }


    final def openStream[Q <: T](p: Tuple => Boolean, observer: Q => Unit): Stream[Q]= {
      val link = NetLink(locator)
      buf.clear()
      buf.putObject(OpenStream(spaceHash, p))
      buf.flip()
      link.write(buf)
      buf.clear()
      val streamHash = readMessage[StreamCookie](link, buf).hash

      log.debug(s">> StreamHash == $streamHash")

      log.debug(s">> Creating Stream Proxy...")
      val proxy = new StreamProxy[Q](link, streamHash, observer)
      proxy
    }

  }



  class ProxyImpl(val locator: Locator) {
    private val link = NetLink(locator)
    private val buf = allocator.allocateDirect(Networking.defaultBufferSize)
    buf.order(LittleEndian)

    private val keyBuf = allocator.allocateDirect(Networking.defaultBufferSize)
    keyBuf.order(LittleEndian)


    final def createSpace(name: String) = {
      buf.clear()
      buf.putObject(CreateSpace(name))
      log.log(buf.toString)
      buf.flip()
      link.write(buf)
      buf.clear()
      readMessage[SpaceHash](link, buf).hash
    }

    final def writeTuple(t: T, spaceHash: Int) {
      buf.clear()
      buf.putObject(WriteTuple(spaceHash, t))
      buf.flip()
      link.write(buf)
      buf.clear()
    }

    final def writeTupleList(tl: List[T], spaceHash: Int) {
      buf.clear()
      buf.putObject(WriteTupleList(spaceHash, tl))
      buf.flip()
      link.write(buf)
      buf.clear()
    }

    final def readTuple[Q <: T](p: Tuple => Boolean, spaceHash: Int): Option[Q] = {
      buf.clear()
      buf.putObject(ReadTuple(spaceHash, p))
      buf.flip()
      link.write(buf)
      buf.clear()

      readMessage[SpaceMessage](link, buf) match {
        case SpaceTuple(hash, t) => Some(t.asInstanceOf[Q])
        case NoMatchingTuple(_) => None
      }
    }


    final def takeTuple[Q <: T](p: Tuple => Boolean, spaceHash: Int): Option[Q] = {
      buf.clear()
      buf.putObject(TakeTuple(spaceHash, p))
      buf.flip()
      link.write(buf)
      buf.clear()
      readMessage[SpaceMessage](link, buf) match {
        case SpaceTuple(hash, t) => Some(t.asInstanceOf[Q])
        case NoMatchingTuple(_) => None
      }
    }

    private def readTupleList[Q](tuples: List[Q], link: NetLink, buf: RawBuffer): List[Q] = {
      buf.clear()
      readMessage[Any](link, buf) match {
        case TListBegin(hash) => {
          readTupleList(tuples, link, buf)
        }
        case TListEnd(hash) => {
          tuples
        }
        case t: Any => {
          val tuple = t.asInstanceOf[Q]
          readTupleList(tuple :: tuples, link, buf)
        }
      }
    }

    final def readAllTuple[Q <: T](p: Tuple => Boolean, spaceHash: Int): List[Q] = {
      buf.clear()
      buf.putObject(ReadAllTuple(spaceHash, p))
      buf.flip()
      link.write(buf)
      buf.clear()
      /*
      readMessage[SpaceMessage](link, buf) match {
        case SpaceTupleList(hash, tuples) => tuples.asInstanceOf[List[Q]]
        case NoMatchingTuple(_) => List()
      }
      */
      readTupleList[Q](List[Q](), link, buf)
    }

    final def takeAllTuple[Q <: T](p: Tuple => Boolean, spaceHash: Int): List[Q] = {
      buf.clear()
      buf.putObject(TakeAllTuple(spaceHash, p))
      buf.flip()
      link.write(buf)
      buf.clear()
      readTupleList(List[Q](), link, buf)
    }


    final def getTuple[Q <: T: Manifest](key: Any, spaceHash: Int): Option[Q]  = {
      keyBuf.clear()
      keyBuf.putKey[Q](key)
      keyBuf.flip()
      buf.clear()
      buf.putObject(GetTuple(spaceHash, keyBuf))
      buf.flip()
      link.write(buf)
      buf.clear()
      readMessage[SpaceMessage](link, buf) match {
        case SpaceTuple(hash, t) => Some(t.asInstanceOf[Q])
        case NoMatchingTuple(_) => None
      }
    }

    def close(): Unit = {
      link.channel.close()
    }
  }

  private val proxy = new ProxyImpl(spaceLoc.locator)
  private val spaceHash = proxy.createSpace(spaceLoc.name)
  private val streamManager = new StreamsManager(spaceLoc.locator, spaceHash)

  /**
   * Write a tuple within this space
   *
   * @param tuple the tuple to be written into the space
   */
  def write(tuple: T) {
    proxy.writeTuple(tuple, spaceHash)
  }

  /**
   * Write a list of tuples within this space
   *
   * @param tuples the tuples to be written into the space
   */
  def write(tuples: List[T]) {
    proxy.writeTupleList(tuples, spaceHash)
  }

  /**
   * Writes a tuple at time t, where t >= Time.now. The ability to write
   * tuples in the future makes it easy to model timer as well as reminders.
   *
   * @param tuple the tuple to be written into the space
   * @param t the time at which the tuple will be written.
   */
  def write(tuple: T, t: Time) {
    throw new NotImplementedError("Operation Not Implemented")
  }

  /**
   * Writes a list of tuples at time t, where t >= Time.now. The ability to write
   * tuples in the future makes it easy to model timer as well as reminders.
   *
   * @param tuples the tuples to be written into the space
   * @param t the time at which the tuple will be written.
   */
  def write(tuples: List[T], t: Time) {
    throw new NotImplementedError("Operation Not Implemented")
  }

  /**
   * Read a tuple that matches the given matcher. A copy of the tuple will be
   * returned to the application. Yet the original tuple will remain within the tuple-space.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def read[Q <: T](p: Tuple => Boolean): Option[Q] = {
    proxy.readTuple[Q](p, spaceHash)
  }


  /**
   * Take a tuple that matches the given matcher. A copy of the tuple will be
   * returned to the application. Yet the original tuple will be removed from the tuple-space.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def take[Q <: T](p: Tuple => Boolean): Option[Q] = proxy.takeTuple[Q](p, spaceHash)


  /**
   * Blocking version of the read operation.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def sread[Q <: T](p: Tuple => Boolean): Option[Q] = ???

  /**
   * Blocking version of the read operation. This operation will block for
   * at most a given amount of time.
   *
   * @param p the predicate used to identify the tuple
   * @param timeout the maximum amount of time for which this call will block
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def sread[Q <: T](p: Tuple => Boolean, timeout: Duration): Option[Q] = ???

  /**
   * Blocking version of the take operation.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def stake[Q <: T](p: Tuple => Boolean): Option[Q] = ???

  /**
   * Blocking version of the take operation. This operation will block for
   * at most a given amount of time.
   *
   * @param p the predicate used to identify the tuple
   * @param timeout the maximum amount of time for which this call will block
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def stake[Q <: T](p: Tuple => Boolean, timeout: Duration): Option[Q] = ???

  /**
   * Read all the tuple that match the given criteria.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return A List of the matching tuples
   */
  def readAll[Q <: T](p: Tuple => Boolean): Iterable[Q] = proxy.readAllTuple(p, spaceHash)


  /**
   * Take all the tuple that match the given criteria.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return A List of the matching tuples
   */
  def takeAll[Q <: T](p: Tuple => Boolean): Iterable[Q] = proxy.takeAllTuple[Q](p, spaceHash)


  /**
   * Execute a function somewhere on this space.
   *
   * @param f the function to execute
   */
  def exec(f: (Space[T]) => Unit) {
    throw new NotImplementedError("Operation not implemented.")
  }

  /**
   * Create a stream that will be pushing updates for a given type T matching the
   * provided matcher.
   *
   * @param p The predicate used to filter tuple-space updates
   * @param observer The observer to be associated wit the Stream
   * @tparam Q The type of the tuple-space entries that will be consumed
   * @return the newly create stream
   */
  def stream[Q <: T](p: Tuple =>  Boolean, observer: Q => Unit): Stream[Q] = streamManager.openStream(p, observer)


  /**
   * Get the tuple with the given key value if present. Otherwise returns None.
   *
   * @param key the key of the tuple to get
   * @tparam Q the type of the tuple
   * @return the tuple if present, None otherwise
   */
  def get[Q <: T: Manifest](key: Key): Option[Q] = proxy.getTuple(key, spaceHash)

  /**
   * Remove the tuple with the given key value if present. Otherwise returns None.
   *
   * @param key the key of the tuple to get
   * @tparam Q the type of the tuple
   * @return the tuple if present, None otherwise
   */
  def remove[Q <: T](key: Key): Option[Q] = ???


  /**
   * Atomically swap the tuple that satisfies the predicate p with the tuple q.
   * @param p a predicate
   * @param q the tuple that will be swapped
   * @tparam Q the type of the tuple
   * @return the swapped out tuple
   */
  def compareAndSwap[Q <:T](p: Tuple => Boolean, q: Q): Option[T] = ???

  /**
   * Create a new space by applying the given function f.
   *
   * @param f the function to apply to the element of the space
   * @tparam Q the target type
   * @return a new space that
   */
  def map[Q <: Tuple](f: (T) => Q): Space[Q] = ???

  /**
   * Return a Space whose elements satisfy the filter predicate
   * @param p the predicate that will be used for selecting the tuples
   * @return the space containing the tuples that satisfy the predicate
   */
  def filter(p: (T) => Boolean): Space[T] = ???

  /**
   * Return a Space whose elements do not satisfy the filter predicate
   * @param p the predicate that will be used for selecting the tuples to drop
   * @return the space containing the tuples that do not satisfy the predicate
   */
  def filterNot(p: (T) => Boolean): Space[T] = ???

  /**
   * Filter the space using a predicate on the key.
   *
   * @param p the key predicate
   * @return the space containing all the matchign tuples
   */
  def filterKeys(p: (SpaceProxy[T]#Key) => Boolean): Space[T] = ???

  /**
   * Aggregate tuples into a single value
   *
   * @param z the initial value
   * @param op the fold operator
   * @tparam R the fold type
   * @return a tuplereresenting the folded eresi
   */
  def fold[R >: (SpaceProxy[T]#Key, T)](z: R)(op: (R, R) => R): R = ???

  /**
   * Create a sub-space hierarchically nested in this space. As an example,
   * the "A" could be the top level Space for "A.B", "A.B.C", etc.
   *
   * In general sub-spaces are a way of organizing spaces and as such a read in to a
   * top-level space will return a value as far as it can find it in the top level or
   * on any one of the nested spaces.
   *
   * @param name the sub-space name
   * @tparam Q the type of the tuple included in the subspace, notice that Q <: T
   * @return the newly create subspace
   */
  def subSpace[Q <: T](name: String): Option[Space[Q]] = ???

  /**
   * Execute a function somewhere on this space.
   *
   * @param f the function to execute
   * @param d the delay after which the function will be executed
   */
  def exec(f: (Space[T]) => Unit, d: Duration) {}

  /**
   * Create a sub-space hierarchically nested in this space. As an example,
   * the "A" could be the top level Space for "A.B", "A.B.C", etc.
   *
   * In general sub-spaces are a way of organizing spaces and as such a read in to a
   * top-level space will return a value as far as it can find it in the top level or
   * on any one of the nested spaces.
   *
   * @param name the sub-space name
   * @tparam Q the type of the tuple included in the subspace, notice that Q <: T
   * @return the newly create subspace
   */
  def createSubSpace[Q <: T](name: String): Space[Q] = ???

  /**
   * Closes the spaces by releasing all the resources.
   */
  def close() {
    proxy.close()
  }
}
