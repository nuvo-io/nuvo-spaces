package nuvo.spaces.local

import nuvo.core.{Tuple, Time, Duration}
import nuvo.spaces.{Stream, Space}
import java.util.concurrent.locks.{Condition, ReentrantLock, ReentrantReadWriteLock}
import nuvo.spaces.SpaceLocator
import nuvo.concurrent.synchronizers._
import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

object LocalSpace {
  def apply[T <: Tuple](l: SpaceLocator) = new LocalSpace[T](l)
  def apply[T <: Tuple](ol: Option[SpaceLocator]) = ol map (l => new LocalSpace[T](l))

  def apply[T <: Tuple](l: SpaceLocator, map: scala.collection.immutable.Map[Any, T]) = new LocalSpace[T](l, map)
  def apply[T <: Tuple](ol: Option[SpaceLocator], map: scala.collection.immutable.Map[Any, T]) = ol map (l => new LocalSpace[T](l, map))
}

class LocalSpace[T <: Tuple](val locator: SpaceLocator, private var map: scala.collection.immutable.Map[Any, T] = scala.collection.immutable.Map[Any, T]()) extends Space[T] {

  type Observer[X] = X => Unit

  // private var map: scala.collection.immutable.Map[Any, T] =

  private val mapRWLock = new ReentrantReadWriteLock()

  private var streams = List[(T => Boolean, Observer[T])]()
  private var streamsData = List[T]()
  private val dispatcherLock = new ReentrantLock()
  private val dispatchQueueNotEmpty = dispatcherLock.newCondition()

  private val subSpaceRWLock = new ReentrantReadWriteLock()
  private var subSpaceMap = scala.collection.immutable.Map[String, LocalSpace[T]]()

  private val sreadListRWLock = new ReentrantReadWriteLock()
  private var sreadList = List[(Tuple => Boolean, Condition, ReentrantLock)]()


  private final val dispatcher = new Thread (new Runnable {
    def run() {
      println("Running: "+ this)
      var isInterrupted = false
      while (!isInterrupted) {
        try {
          // println(">>> Waiting for data")
          syncrhonized(dispatcherLock) {
            dispatchQueueNotEmpty.await()
          }
          //println(">>> Got data!!!")
          val s  = streams
          val d = streamsData
          d.foreach( t => s.foreach(s => if (s._1(t)) s._2(t)))

          val srl = sreadList

          srl foreach { e =>
            streamsData.find(e._1).foreach { x =>
              syncrhonized(e._3) {
                e._2.signalAll()
              }
            }
          }

        } catch {
          case ie: InterruptedException => isInterrupted = true
        }
      } } } )

  dispatcher.start()

  def write(t: T) {
    synchronizedWrite(mapRWLock) {
      map = map + (t.key ->  t)
    }
    // reference assignment is atomic in the VM.
    syncrhonized(dispatcherLock) {
      if (!streams.isEmpty || !sreadList.isEmpty) {
        streamsData = streamsData ::: List(t)
        dispatchQueueNotEmpty.signalAll()
      }
    }
  }


  /**
   * Writes a tuple at time t, where t >= Time.now. The ability to write
   * tuples in the future makes it easy to model timer as well as reminders.
   *
   * @param e the tuple to be written into the space
   * @param t the time at which the tuple will be written.
   */
  def write(e: T, t: Time) {
    //TODO: Should not use future here since it could block after consuming all
    // available threads. A better approach is to use a queue to store the
    // tuple on a list and have a timer that schedules insertions.
    if (t <= Time.now) synchronizedWrite(mapRWLock) { this.write(e) }
    else future {
      wait((t-Time.now).duration)
      this.write(e)
    }
  }

  def read[Q <: T](p: Tuple => Boolean): Option[Q] = {
    val currentMap = map
    val res = currentMap.find(t => p(t._2))
    res map { _._2.asInstanceOf[Q]}
  }

  def rread[Q <: T](p: Tuple => Boolean): Option[Q] = {
    val currentMap = map
    currentMap.par.find(t => p(t._2)).map(_._2.asInstanceOf[Q]) match {
      case t: Some[_] => t
      case None => {
        val currentSubSpaceMap = subSpaceMap
        val  it = currentSubSpaceMap.iterator

        var t: Option[Q] = None
        while(it.hasNext && t == None) {
          t = it.next()._2.read[Q](p)
        }
        t
      }
    }

  }

  def take[Q <: T](p: Tuple => Boolean): Option[Q] = synchronizedWrite(mapRWLock) {
    val r = map.par.find(t => p(t._2)).map(_._2.asInstanceOf[Q])
    r.map(se => map = (map - se.key))
    r
  }

  def readAll[Q <: T](p: Tuple => Boolean): Iterable[Q] = {
    val currentMap = map
    val pi = currentMap.par.filter(t => p(t._2)).map(_._2.asInstanceOf[Q])
    pi.seq
  }


  def takeAll[Q <: T](p: Tuple => Boolean): Iterable[Q] =  synchronizedWrite(mapRWLock) {
    val res = readAll[Q](p)
    res.foreach(se => map = (map - se.key))
    res
  }

  def exec(f: (Space[T]) => Unit) {
    future {
      f(this)
    }
  }

  def exec(f: (Space[T]) => Unit, d: Duration) {

  }

  def stream[Q <: T](p: Tuple => Boolean, observer: Observer[Q]): Stream[Q] = {
    val s = new LocalStream[Q]
    s.observers += observer
    streams = (p, s) :: streams
    s

  }

  /*
  def stream[Q <: T](p: Tuple => Boolean): Stream[Q] = {
    val s = new LocalStream[Q]
    streams += ((p, s))
    s
  }
  */

  /**
   * Blocking version of the read operation.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def sread[Q <: T](p: Tuple => Boolean): Option[Q] = {
    read[Q](p) orElse {
      val lock = new ReentrantLock()
      val condition = lock.newCondition()
      var result: Option[Q] = None
      do {
        synchronizedWrite(sreadListRWLock) {
          sreadList = (p, condition, lock) :: sreadList
        }
        syncrhonized(lock) {
          condition.await()
          result = read[Q](p)
        }
      } while (result == None)
      result
    }
  }

  /**
   * Blocking version of the read operation. This operation will block for
   * at most a given amount of time.
   *
   * @param p the predicate used to identify the tuple
   * @param timeout the maximum amount of time for which this call will block
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def sread[Q <: T](p: Tuple => Boolean, timeout: Duration): Option[Q] = {
    read[Q](p) orElse {
      val lock = new ReentrantLock()
      val condition = lock.newCondition()
      var result: Option[Q] = None

      synchronizedWrite(sreadListRWLock) {
        sreadList = (p, condition, lock) :: sreadList
      }
      syncrhonized(lock) {
        condition.await(timeout.duration, TimeUnit.MILLISECONDS)
        result = read[Q](p)
      }
      result
    }
  }

  /**
   * Blocking version of the take operation.
   *
   * @param p the predicate used to identify the tuple
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def stake[Q <: T](p: Tuple => Boolean): Option[Q] = {
    take[Q](p) orElse {
      val lock = new ReentrantLock()
      val condition = lock.newCondition()
      var result: Option[Q] = None
      do {
        synchronizedWrite(sreadListRWLock) {
          sreadList = (p, condition, lock) :: sreadList
        }
        syncrhonized(lock) {
          condition.await()
          result = take[Q](p)
        }
      } while (result == None)
      result
    }
  }

  /**
   * Blocking version of the take operation. This operation will block for
   * at most a given amount of time.
   *
   * @param p the predicate used to identify the tuple
   * @param timeout the maximum amount of time for which this call will block
   * @tparam Q the typle type
   * @return Some tuple if the matching is successful, None otherwise
   */
  def stake[Q <: T](p: Tuple => Boolean, timeout: Duration): Option[Q] = {
    take[Q](p) orElse {
      val lock = new ReentrantLock()
      val condition = lock.newCondition()
      var result: Option[Q] = None

      synchronizedWrite(sreadListRWLock) {
        sreadList = (p, condition, lock) :: sreadList
      }
      syncrhonized(lock) {
        condition.await(timeout.duration, TimeUnit.MILLISECONDS)
        result = take[Q](p)
      }
      result
    }
  }


  /**
   * Get the tuple with the given key value if present. Otherwise returns None.
   *
   * @param key the key of the tuple to get
   * @tparam Q the type of the tuple
   * @return the tuple if present, None otherwise
   */
  def get[Q <: T: Manifest](key: LocalSpace[T]#Key): Option[Q] =  {
    val currentMap = map
    currentMap.get(key).map(_.asInstanceOf[Q])
  }

  /**
   * Remove the tuple with the given key value if present. Otherwise returns None.
   *
   * @param key the key of the tuple to get
   * @tparam Q the type of the tuple
   * @return the tuple if present, None otherwise
   */
  def remove[Q <: T](key: LocalSpace[T]#Key): Option[Q] = synchronizedWrite(mapRWLock) {
    val r = get(key)
    r match {
      case Some(t) => map = (map -  key)
      case None =>
    }
    r
  }

  /**
   * Write a list of tuples within this space
   *
   * @param tuples the tuples to be written into the space
   */
  def write(tuples: List[T]) =  {
    tuples.foreach(this.write(_))
  }

  /**
   * Writes a list of tuples at time t, where t >= Time.now. The ability to write
   * tuples in the future makes it easy to model timer as well as reminders.
   *
   * @param tuples the tuples to be written into the space
   * @param t the time at which the tuple will be written.
   */
  def write(tuples: List[T], t: Time) = {
    tuples.foreach(this.write(_, t))
  }

  /**
   * Atomically swap the tuple that satisfies the predicate p with the tuple q.
   * @param p a predicate
   * @param q the tuple that will be swapped
   * @tparam Q the type of the tuple
   * @return the swapped out tuple
   */
  def compareAndSwap[Q <: T](p: Tuple => Boolean, q: Q): Option[T] = synchronizedWrite(mapRWLock) {
    val r = this.take(p)
    r.map(v => this.write(q))
    r
  }

  /**
   * Create a new space by applying the given function f.
   *
   * @param f the function to apply to the element of the space
   * @tparam Q the target type
   * @return a new space that
   */
  def map[Q <: Tuple](f: (T) => Q): Space[Q] = {
    val currentMap = map
    val l = SpaceLocator("tmp$" +locator.name + ".map").get
    LocalSpace[Q](l, currentMap.mapValues(f))
  }

  /**
   * Return a Space whose elements satisfy the filter predicate
   * @param p the predicate that will be used for selecting the tuples
   * @return the space containing the tuples that satisfy the predicate
   */
  def filter(p: (T) => Boolean): Space[T] =  {
    val currentMap = map
    val l = SpaceLocator("tmp$" +locator.name + ".filter").get
    LocalSpace[T](l, currentMap.filter(t => p(t._2)))
  }

  /**
   * Return a Space whose elements do not satisfy the filter predicate
   * @param p the predicate that will be used for selecting the tuples to drop
   * @return the space containing the tuples that do not satisfy the predicate
   */
  def filterNot(p: (T) => Boolean): Space[T] = {
    val currentMap = map
    val l = SpaceLocator("tmp$" +locator.name + ".filterNot").get
    LocalSpace[T](l, currentMap.filterNot(t => p(t._2)))
  }

  /**
   * Filter the space using a predicate on the key.
   *
   * @param p the key predicate
   * @return the space containing all the matchign tuples
   */
  def filterKeys(p: (LocalSpace[T]#Key) => Boolean): Space[T] = {
    val currentMap = map
    val l = SpaceLocator("tmp$" +locator.name + ".filterKeys").get
    LocalSpace[T](l, currentMap.filter(p))
  }


  /**
   * Aggregate tuples into a single value
   *
   * @param z the initial value
   * @param op the fold operator
   * @tparam R the fold type
   * @return a tuplereresenting the folded eresi
   */
  def fold[R >: (LocalSpace[T]#Key, T)](z: R)(op: (R, R) => R): R = {
    val currentMap = map
    currentMap.fold(z)(op)
  }

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
  def createSubSpace[Q <: T](name: String): Space[Q] = synchronizedRead(subSpaceRWLock) {
    (subSpaceMap get (name)).getOrElse {
      val l = SpaceLocator(name).get
      val subs = LocalSpace[Q](l).asInstanceOf[LocalSpace[T]]
      subSpaceMap = subSpaceMap + (name -> subs)
    }.asInstanceOf[Space[Q]]
  }

  def subSpace[Q <: T](name: String) = subSpaceMap get(name) map { _.asInstanceOf[Space[Q]] }

  def close() {
    dispatcher.interrupt()
  }

}
