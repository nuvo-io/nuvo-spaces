package nuvo.spaces.remote

import nuvo.net._
import java.net.{StandardSocketOptions, InetAddress, InetSocketAddress}
import java.nio.channels.{SelectionKey, ServerSocketChannel, SocketChannel}

import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.HashMap

import nuvo.spaces.{Space}
import nuvo.spaces.local.LocalSpace
import nuvo.nio._
import nuvo.nio.prelude._
import nuvo.runtime.Config._
import nuvo.core.Tuple
import nuvo.concurrent.synchronizers._
import java.util.concurrent.locks.ReentrantReadWriteLock
import nuvo.net.prelude._
import nuvo.concurrent.LFIOProcessor
import nuvo.net.MessagePayload


import nuvo.spaces.Config
import nuvo.spaces.prelude.SpaceServerRegistry

/**
 * This class implements a Space as a simple remote. Notice that this implementation does not provide any
 * fault-tolerance and is mostly useful for testing purposes.
 */

case class KGetTuple(hash: Int, key: Any) extends SpaceMessage
case class KRemoveTuple(hash: Int, key: Any) extends SpaceMessage

class SpaceServer(val locator: Locator) {

  SpaceServerRegistry.registerSpaceServer(locator.toString(), this)

  private var running = true
  val MaxMsgSize = Config.spaceBufSize

  val spaceNameMap = new HashMap[String, Int]()
  private val spaceNameMapRWLock = new ReentrantReadWriteLock()

  val spaceMap = new HashMap[Int, Space[Tuple]]()
  private val spaceMapRWLock = new ReentrantReadWriteLock()

  val streamMap = new HashMap[Int, List[(Tuple => Boolean, Int, SocketChannel)]]


  def localCreateSpace[T <: Tuple](spaceName: String): Option[Space[T]] = {
    synchronizedWrite(spaceNameMapRWLock) {
      (for (hash <- spaceNameMap.get(spaceName);
            space <- spaceMap.get(hash))
      yield space.asInstanceOf[Space[T]]).orElse {
        val hash = spaceName.hashCode
        spaceNameMap += (spaceName -> hash)
        synchronizedWrite(spaceMapRWLock) {
          Space[T]().map { s =>
            spaceMap += (hash -> s.asInstanceOf[Space[Tuple]])
            s
          }
        }
      }
    }
  }



  object SpaceServerProtocol {

    final class StreamHandler(val streamHash: Int, val cid: Long, mp: MessagePump) extends Function[Tuple, Unit] {
      private val buf = allocator.allocateDirect()

      final def apply(t: Tuple) {
        buf.clear()
        buf.putObject(StreamTuple(streamHash, t))
        buf.flip()
        mp.writeTo(buf, cid)
      }
    }


    def react(sm: SpaceMessage, m: MessagePayload): Option[SpaceMessage] = {
      val buf = m.buf.clear()
      val cid = m.cid
      val mp = m.mp
      sm match {

        case CreateSpace(spaceName) => {
          val someHash = synchronizedRead(spaceNameMapRWLock) {
            spaceNameMap.get(spaceName)
          }
          val hash = someHash.getOrElse {
            val h = spaceName.hashCode
            synchronizedWrite(spaceNameMapRWLock) {
              spaceNameMap += (spaceName -> h)
              synchronizedWrite(spaceMapRWLock) {
                import nuvo.spaces.prelude.LocalSpace._
                Space[Tuple]() map {s =>
                  spaceMap += (h -> s)
                }
              }
            }
            h
          }
          Some(SpaceHash(spaceName, hash))
        }
        // TODO: Should we remove this op and only rely on Create
        case LookupSpace(spaceName) => {
          spaceNameMap.get(spaceName).flatMap(h => Some(SpaceHash(spaceName, h))).orElse(Some(SpaceHash(spaceName, 0)))
        }

        case OpenStream(spaceHash, p) => {
          val streamHash = p.hashCode()
          // TODO: the react should not have side-effects. This code should be moved
          // in the layer that deals with effects.
          synchronizedRead(spaceMapRWLock) { spaceMap.get(spaceHash) } match {
            case Some(space) => {

              // Note: We need to make sure that the stream coockie is sent before
              // we start dispatching any tuple.
              buf.clear()
              buf.putObject(StreamCookie(streamHash))
              buf.flip()
              mp.writeTo(buf, cid)
              buf.clear()

              // TODO: We need to store the returned stream somewhere in order to
              // properly deal with the "close" operation.
              //
              val handler = new StreamHandler(streamHash, cid, mp)
              val s = space.stream(p, handler)
              None
            }
            // TODO: Should add an explicit error message
            case None => Some(StreamCookie(0))
          }
        }
        case ReadTuple(hash, p) => {
          val tuple = synchronizedRead(spaceMapRWLock){ spaceMap get(hash) } flatMap(_.read[Tuple](p))

          tuple match {
            case Some(t) => Some(SpaceTuple(hash, t))
            case None => Some(NoMatchingTuple( hash))
          }
        }

        case TakeTuple(hash, p) => {
          val tuple = synchronizedRead(spaceMapRWLock){ spaceMap get hash } flatMap(_.take[Tuple](p))

          tuple match {
            case Some(t) => Some(SpaceTuple(hash, t))
            case None => Some(NoMatchingTuple( hash))
          }
        }

        // case
        case ReadAllTuple(hash, p) => {
          // log.// log(s"ReadAllTuple($hash, $p)")
          // log.debug("Printing all Tuples...")
          // spaceMap get(hash) map { space => (space.readAll({case _ => true})).foreach(println)}
          // log.debug("... Done.")

          val tuples = synchronizedRead(spaceMapRWLock){ spaceMap get hash } match {
            case Some(space) => space.readAll[Tuple](p).toList
            case None => List()
          }

          Some(SpaceTupleList(hash, tuples))
        }

        case TakeAllTuple(hash, p) => {
          // log.// log(s"ReadAllTuple($hash, $p)")
          // log.debug("Printing all Tuples...")
          // spaceMap get(hash) map { space => (space.readAll({case _ => true})).foreach(println)}
          // log.debug("... Done.")

          val tuples = synchronizedRead(spaceMapRWLock){ spaceMap get hash } match {
            case Some(space) => space.takeAll[Tuple](p).toList
            case None => List()
          }

          Some(SpaceTupleList(hash, tuples))
        }


        case WriteTuple(hash, t) => {
          // log.debug(s"WriteTuple($hash, $t)")
          synchronizedRead(spaceMapRWLock){ spaceMap get hash } map(_.write(t))
          None
        }

        case WriteTupleList(hash, tl) => {
          synchronizedRead(spaceMapRWLock){ spaceMap get hash } map(_.write(tl))
          None
        }


        case GetTuple(hash, keyBuf) => {
          val key = keyBuf.getKey[Any]
          synchronizedRead(spaceMapRWLock){ spaceMap get hash } flatMap(_.get[Tuple](key)) match {
            case Some(t) => {
              Some(SpaceTuple(hash, t))
            }
            case None => {
              Some(NoMatchingTuple(hash))
            }
          }
        }

        case KRemoveTuple(hash, key) => {
          synchronizedRead(spaceMapRWLock) {spaceMap get(hash) } flatMap(_.remove[Tuple](key)) match {
            case Some(t) => Some(SpaceTuple(hash, t))
            case None => Some(NoMatchingTuple(hash))
          }
        }
        case _ => None
      }
    }
  }

  /*

  final def readKey(c: SocketChannel, buf: RawBuffer): Any = {

    val typeName = receiveMessage(c, buf)
    // log.debug(s"Parsed Type Name = $typeName")
    val (oserializers, kserializers) = SerializerCache.lookup(typeName).getOrElse (
    {
      SerializerCache.registerType(typeName)
      SerializerCache.lookup(typeName).get
    })

    kserializers._2.map(_.invoke(null, buf).asInstanceOf[Any]).get
  }
  */

  def start() {

    val processor = (m: MessagePayload) => {
      val msgBuf = m.buf
      val msg = msgBuf.getObject[SpaceMessage]
      val action = SpaceServerProtocol.react(msg, m)

      msgBuf.clear()

      action.map {
        case SpaceTupleList(hash, ts) => {
          msgBuf.putObject(TListBegin(hash))
          msgBuf.flip()
          m.mp.writeTo(msgBuf, m.cid)
          ts foreach { t =>
            msgBuf.clear()
            msgBuf.putObject(t)
            msgBuf.flip()
            m.mp.writeTo(msgBuf, m.cid)
          }
          msgBuf.clear()
          msgBuf.putObject(TListEnd(hash))
          msgBuf.flip()
          m.mp.writeTo(msgBuf, m.cid)
        }
        case a @ _ => {
          msgBuf.putObject(a)
          msgBuf.flip()
          m.mp.writeTo(msgBuf, m.cid)
        }
      }
    }

    val iop = new LFIOProcessor (
      Concurrency.threadPoolSize,
      Networking.defaultBufferSize,
      Concurrency.threadPoolQueueSize,
      processor
    )

    val reader = (k: SelectionKey, buf: RawBuffer) => {
      val channel = k.channel().asInstanceOf[SocketChannel]
      tcpNuvoSelector(channel, buf)
    }


    val mp = new TCPMessagePump(locator, reader, Networking.defaultBufferSize, m => iop.process(m))
    iop.start()
    mp.start()

    /*
          c.setOption[java.lang.Boolean](StandardSocketOptions.TCP_NODELAY, true)
      c.setOption[java.lang.Integer](StandardSocketOptions.SO_SNDBUF, 8192)
      c.setOption[java.lang.Integer](StandardSocketOptions.SO_RCVBUF, 8192)

     */
  }
}

object SpaceServer {

  def main(args: Array[String]) {

    if (args.length > 0) {
      Locator(args(0)) map { l =>
        val server = new SpaceServer(l)
        server.start()
        log.log("Space Server Started for locator: " + args(0))
      } getOrElse {
        log.error("Invalid Locator: " + args(0))
      }
    }
    else println("USAGE: SpaceServer <locator>")

  }
}
