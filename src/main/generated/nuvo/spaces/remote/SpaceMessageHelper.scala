package nuvo.spaces.remote

import nuvo.nio.prelude._
import nuvo.nio._
import nuvo.core.Tuple

object SpaceMessageTypeRegistration {
  val typeList = List("nuvo.spaces.remote.CreateSpace", "nuvo.spaces.remote.LookupSpace", "nuvo.spaces.remote.DeleteSpace", "nuvo.spaces.remote.SpaceHash", "nuvo.spaces.remote.ReadTuple", "nuvo.spaces.remote.ReadAllTuple", "nuvo.spaces.remote.TakeTuple", "nuvo.spaces.remote.TakeAllTuple", "nuvo.spaces.remote.SpaceTuple", "nuvo.spaces.remote.NoMatchingTuple", "nuvo.spaces.remote.SpaceTupleList", "nuvo.spaces.remote.TListBegin", "nuvo.spaces.remote.TListEnd", "nuvo.spaces.remote.OpenStream", "nuvo.spaces.remote.CloseStream", "nuvo.spaces.remote.StreamCookie", "nuvo.spaces.remote.StreamTuple", "nuvo.spaces.remote.StreamTupleList", "nuvo.spaces.remote.CompareAndSwap", "nuvo.spaces.remote.WriteTuple", "nuvo.spaces.remote.WriteTupleList", "nuvo.spaces.remote.GetTuple", "nuvo.spaces.remote.RemoveTuple")

  def registerTypes(): Boolean = {
    typeList.foreach { t =>
      println(s"Registering Type: $t")
      nuvo.nio.SerializerCache.registerType(t)}
    true
  }
  val registerTypeOK = registerTypes()
}

object CreateSpaceHelper {

  val typeHash = (-407130179728225500L, 4634148254998904331L) // nuvo.spaces.remote.CreateSpace
  def serialize(buf: RawBuffer, t: CreateSpace, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: CreateSpace) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putString(t.spaceName)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):CreateSpace = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : CreateSpace = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val spaceName = buf.getString()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new CreateSpace(spaceName )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : CreateSpace = {
    val spaceName = buf.getString()
    new CreateSpace(spaceName )
  }
  
}

object LookupSpaceHelper {

  val typeHash = (6297095591217523147L, 7361450153473930237L) // nuvo.spaces.remote.LookupSpace
  def serialize(buf: RawBuffer, t: LookupSpace, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: LookupSpace) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putString(t.spaceName)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):LookupSpace = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : LookupSpace = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val spaceName = buf.getString()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new LookupSpace(spaceName )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : LookupSpace = {
    val spaceName = buf.getString()
    new LookupSpace(spaceName )
  }
  
}

object DeleteSpaceHelper {

  val typeHash = (-8795646180366988554L, -1277124883879778219L) // nuvo.spaces.remote.DeleteSpace
  def serialize(buf: RawBuffer, t: DeleteSpace, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: DeleteSpace) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putLong(t.hash)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):DeleteSpace = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : DeleteSpace = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getLong()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new DeleteSpace(hash )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : DeleteSpace = {
    val hash = buf.getLong()
    new DeleteSpace(hash )
  }
  
}

object SpaceHashHelper {

  val typeHash = (6284316788306760040L, -1191779380796843506L) // nuvo.spaces.remote.SpaceHash
  def serialize(buf: RawBuffer, t: SpaceHash, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: SpaceHash) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putString(t.spaceName)
    buf.putInt(t.hash)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):SpaceHash = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : SpaceHash = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val spaceName = buf.getString()
    val hash = buf.getInt()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new SpaceHash(spaceName , hash)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : SpaceHash = {
    val spaceName = buf.getString()
    val hash = buf.getInt()
    new SpaceHash(spaceName , hash)
  }
  
}

object ReadTupleHelper {

  val typeHash = (-582343344590942928L, 753033646326046042L) // nuvo.spaces.remote.ReadTuple
  def serialize(buf: RawBuffer, t: ReadTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: ReadTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.p, JavaSF)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):ReadTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : ReadTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new ReadTuple(hash , p)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : ReadTuple = {
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    new ReadTuple(hash , p)
  }
  
}

object ReadAllTupleHelper {

  val typeHash = (-4288926004274338257L, -9080535407976572906L) // nuvo.spaces.remote.ReadAllTuple
  def serialize(buf: RawBuffer, t: ReadAllTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: ReadAllTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.p, JavaSF)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):ReadAllTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : ReadAllTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new ReadAllTuple(hash , p)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : ReadAllTuple = {
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    new ReadAllTuple(hash , p)
  }
  
}

object TakeTupleHelper {

  val typeHash = (-1131290836018367046L, 383700751759173018L) // nuvo.spaces.remote.TakeTuple
  def serialize(buf: RawBuffer, t: TakeTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: TakeTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.p, JavaSF)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):TakeTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : TakeTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new TakeTuple(hash , p)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : TakeTuple = {
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    new TakeTuple(hash , p)
  }
  
}

object TakeAllTupleHelper {

  val typeHash = (9090709959395705256L, 4156346409497354335L) // nuvo.spaces.remote.TakeAllTuple
  def serialize(buf: RawBuffer, t: TakeAllTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: TakeAllTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.p, JavaSF)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):TakeAllTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : TakeAllTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new TakeAllTuple(hash , p)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : TakeAllTuple = {
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    new TakeAllTuple(hash , p)
  }
  
}

object SpaceTupleHelper {

  val typeHash = (-583665761046423683L, 7065387199239664355L) // nuvo.spaces.remote.SpaceTuple
  def serialize(buf: RawBuffer, t: SpaceTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: SpaceTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.t)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):SpaceTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : SpaceTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val t = buf.getObject[Tuple]()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new SpaceTuple(hash , t)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : SpaceTuple = {
    val hash = buf.getInt()
    val t = buf.getObject[Tuple]()
    new SpaceTuple(hash , t)
  }
  
}

object NoMatchingTupleHelper {

  val typeHash = (4368311601149981823L, -836904394381970338L) // nuvo.spaces.remote.NoMatchingTuple
  def serialize(buf: RawBuffer, t: NoMatchingTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: NoMatchingTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):NoMatchingTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : NoMatchingTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new NoMatchingTuple(hash )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : NoMatchingTuple = {
    val hash = buf.getInt()
    new NoMatchingTuple(hash )
  }
  
}

object SpaceTupleListHelper {

  val typeHash = (-4824980723609769088L, 7507252539792038175L) // nuvo.spaces.remote.SpaceTupleList
  def serialize(buf: RawBuffer, t: SpaceTupleList, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: SpaceTupleList) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putInt(t.tl.length)
    t.tl.foreach { x => buf.putObject(x) }
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):SpaceTupleList = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : SpaceTupleList = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val __nuvoc_tlLen = buf.getInt()
    val tl = (1 to __nuvoc_tlLen map { x => buf.getObject[Tuple]() }).toList
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new SpaceTupleList(hash , tl)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : SpaceTupleList = {
    val hash = buf.getInt()
    val __nuvoc_tlLen = buf.getInt()
    val tl = (1 to __nuvoc_tlLen map { x => buf.getObject[Tuple]() }).toList
    new SpaceTupleList(hash , tl)
  }
  
}

object TListBeginHelper {

  val typeHash = (-6993489000274783108L, -6098275586727833584L) // nuvo.spaces.remote.TListBegin
  def serialize(buf: RawBuffer, t: TListBegin, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: TListBegin) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):TListBegin = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : TListBegin = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new TListBegin(hash )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : TListBegin = {
    val hash = buf.getInt()
    new TListBegin(hash )
  }
  
}

object TListEndHelper {

  val typeHash = (4788638500608140499L, -4162645938437190719L) // nuvo.spaces.remote.TListEnd
  def serialize(buf: RawBuffer, t: TListEnd, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: TListEnd) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):TListEnd = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : TListEnd = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new TListEnd(hash )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : TListEnd = {
    val hash = buf.getInt()
    new TListEnd(hash )
  }
  
}

object OpenStreamHelper {

  val typeHash = (2466156894099232008L, 6804532098224953344L) // nuvo.spaces.remote.OpenStream
  def serialize(buf: RawBuffer, t: OpenStream, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: OpenStream) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.p, JavaSF)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):OpenStream = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : OpenStream = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new OpenStream(hash , p)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : OpenStream = {
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    new OpenStream(hash , p)
  }
  
}

object CloseStreamHelper {

  val typeHash = (7453972488240560909L, 4803213351945067945L) // nuvo.spaces.remote.CloseStream
  def serialize(buf: RawBuffer, t: CloseStream, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: CloseStream) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):CloseStream = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : CloseStream = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new CloseStream(hash )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : CloseStream = {
    val hash = buf.getInt()
    new CloseStream(hash )
  }
  
}

object StreamCookieHelper {

  val typeHash = (8106197120594687576L, -5265466686887374634L) // nuvo.spaces.remote.StreamCookie
  def serialize(buf: RawBuffer, t: StreamCookie, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: StreamCookie) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):StreamCookie = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : StreamCookie = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new StreamCookie(hash )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : StreamCookie = {
    val hash = buf.getInt()
    new StreamCookie(hash )
  }
  
}

object StreamTupleHelper {

  val typeHash = (-4245022351097444486L, -5794363316213288295L) // nuvo.spaces.remote.StreamTuple
  def serialize(buf: RawBuffer, t: StreamTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: StreamTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.t)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):StreamTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : StreamTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val t = buf.getObject[Tuple]()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new StreamTuple(hash , t)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : StreamTuple = {
    val hash = buf.getInt()
    val t = buf.getObject[Tuple]()
    new StreamTuple(hash , t)
  }
  
}

object StreamTupleListHelper {

  val typeHash = (4476307096311334149L, -3054747321528595080L) // nuvo.spaces.remote.StreamTupleList
  def serialize(buf: RawBuffer, t: StreamTupleList, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: StreamTupleList) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putInt(t.t.length)
    t.t.foreach { x => buf.putObject(x) }
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):StreamTupleList = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : StreamTupleList = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val __nuvoc_tLen = buf.getInt()
    val t = (1 to __nuvoc_tLen map { x => buf.getObject[Tuple]() }).toList
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new StreamTupleList(hash , t)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : StreamTupleList = {
    val hash = buf.getInt()
    val __nuvoc_tLen = buf.getInt()
    val t = (1 to __nuvoc_tLen map { x => buf.getObject[Tuple]() }).toList
    new StreamTupleList(hash , t)
  }
  
}

object CompareAndSwapHelper {

  val typeHash = (8725361773401520068L, 7000317173434934853L) // nuvo.spaces.remote.CompareAndSwap
  def serialize(buf: RawBuffer, t: CompareAndSwap, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: CompareAndSwap) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.p, JavaSF)
    buf.putObject(t.t)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):CompareAndSwap = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : CompareAndSwap = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    val t = buf.getObject[Tuple]()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new CompareAndSwap(hash , p, t)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : CompareAndSwap = {
    val hash = buf.getInt()
    val p = buf.getObject[Tuple => Boolean](JavaSF)
    val t = buf.getObject[Tuple]()
    new CompareAndSwap(hash , p, t)
  }
  
}

object WriteTupleHelper {

  val typeHash = (3556346562906803607L, -219189095298722236L) // nuvo.spaces.remote.WriteTuple
  def serialize(buf: RawBuffer, t: WriteTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: WriteTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putObject(t.t)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):WriteTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : WriteTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val t = buf.getObject[Tuple]()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new WriteTuple(hash , t)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : WriteTuple = {
    val hash = buf.getInt()
    val t = buf.getObject[Tuple]()
    new WriteTuple(hash , t)
  }
  
}

object WriteTupleListHelper {

  val typeHash = (7460197252168201177L, 8277292090047450144L) // nuvo.spaces.remote.WriteTupleList
  def serialize(buf: RawBuffer, t: WriteTupleList, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: WriteTupleList) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    buf.putInt(t.tl.length)
    t.tl.foreach { x => buf.putObject(x) }
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):WriteTupleList = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : WriteTupleList = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val __nuvoc_tlLen = buf.getInt()
    val tl = (1 to __nuvoc_tlLen map { x => buf.getObject[Tuple]() }).toList
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new WriteTupleList(hash , tl)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : WriteTupleList = {
    val hash = buf.getInt()
    val __nuvoc_tlLen = buf.getInt()
    val tl = (1 to __nuvoc_tlLen map { x => buf.getObject[Tuple]() }).toList
    new WriteTupleList(hash , tl)
  }
  
}

object GetTupleHelper {

  val typeHash = (5822898276415479830L, -2406051919910960729L) // nuvo.spaces.remote.GetTuple
  def serialize(buf: RawBuffer, t: GetTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: GetTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    val __nuvoc_pos = t.key.position()
    val __nuvoc_lim = t.key.limit()
    buf.putInt(__nuvoc_lim - __nuvoc_pos)
    buf.put(t.key)
    t.key.position(__nuvoc_pos)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):GetTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : GetTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    val __nuvoc_keyLen = buf.getInt()
    val __nuvoc_bufPos = buf.position
    val __nuvoc_bufLimit = buf.limit()
    val key = RawBuffer.allocate(__nuvoc_keyLen)
    buf.limit(__nuvoc_bufPos + __nuvoc_keyLen)
    key.put(buf)
    buf.limit(__nuvoc_bufLimit)
    key.flip()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new GetTuple(hash , key)
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : GetTuple = {
    val hash = buf.getInt()
    val __nuvoc_keyLen = buf.getInt()
    val __nuvoc_bufPos = buf.position
    val __nuvoc_bufLimit = buf.limit()
    val key = RawBuffer.allocate(__nuvoc_keyLen)
    buf.limit(__nuvoc_bufPos + __nuvoc_keyLen)
    key.put(buf)
    buf.limit(__nuvoc_bufLimit)
    key.flip()
    new GetTuple(hash , key)
  }
  
}

object RemoveTupleHelper {

  val typeHash = (-3942198800847292815L, -2740260942907558061L) // nuvo.spaces.remote.RemoveTuple
  def serialize(buf: RawBuffer, t: RemoveTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: RemoveTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putLong(typeHash._1)
    buf.putLong(typeHash._2)
    buf.putInt(t.hash)
    val __nuvoc_serializedDataLength = buf.position - __nuvoc_startPosition - 4
    val __nuvoc_MEL = (buf.order.value << 24) | (__nuvoc_serializedDataLength & 0x00ffffff)
    buf.order(ByteOrder.littleEndian)
    buf.putInt(__nuvoc_startPosition, __nuvoc_MEL)
  }
  
  def deserialize(buf: RawBuffer,format: SerializationFormat):RemoveTuple = {
    format match {
    case NuvoSF  => deserializeNuvoSF(buf)
    }
  }
  
  final def deserializeNuvoSF(buf: RawBuffer) : RemoveTuple = {
    buf.order(LittleEndian)
    val __nuvoc_MEL = buf.getInt()
    val __nuvoc_endianess =  (__nuvoc_MEL >> 24).toByte
    val __nuvoc_serializeDataLength =  (__nuvoc_MEL & 0x00ffffff)
    buf.order(__nuvoc_endianess match { case LittleEndian.value => LittleEndian; case BigEndian.value  => BigEndian; case _ => { buf.position(buf.position + __nuvoc_serializeDataLength) ; throw new RuntimeException("Invalid Format")}})
    val __nuvoc_startPosition =  buf.position
    val wireTypeHash = (buf.getLong, buf.getLong)
    if (typeHash != wireTypeHash) throw new RuntimeException("Mismatching TypeHash, you ma be trying to deserialize using the wrong helper")
    val hash = buf.getInt()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new RemoveTuple(hash )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : RemoveTuple = {
    val hash = buf.getInt()
    new RemoveTuple(hash )
  }
  
}

