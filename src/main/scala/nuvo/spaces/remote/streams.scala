package nuvo.spaces.remote

import nuvo.nio.{SerializerCache, BigEndian, LittleEndian, RawBuffer}
import nuvo.nio.prelude._
import java.nio.channels.SocketChannel
import nuvo.net.NetLink


package object streams {
  private [spaces] final def getMessage(link: NetLink, buf: RawBuffer): String = {

    buf.clear()
    val hbpos = buf.capacity - 4

    buf.position(hbpos)
    do {
      link.read(buf)
    }
    while (buf.position != buf.capacity)

    buf.position(hbpos)
    val MEL = buf.getInt()
    val E = MEL >> 24

    val length = MEL & 0x00ffffff

    buf.order( E match {
      case LittleEndian.value => {
        LittleEndian
      }
      case BigEndian.value => {
        BigEndian
      }
      case _ => throw new RuntimeException("Currupted Stream")
    })

    buf.position(buf.capacity - length)
    do {
      val l = link.read(buf)
    } while (buf.position != buf.capacity)
    buf.position(buf.capacity - length)
    buf.getString()
  }

  final def readMessage[T](link: NetLink, buf: RawBuffer) = {
    val typeName = getMessage(link, buf)
    val (oserializers, kserializers) = SerializerCache.lookup(typeName).getOrElse (
    {
      SerializerCache.registerType(typeName)
      SerializerCache.lookup(typeName).get
    })

    val msg = oserializers._2.map(_.invoke(null, buf).asInstanceOf[T]).get
    msg
  }

}
