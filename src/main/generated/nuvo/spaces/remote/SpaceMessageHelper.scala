package nuvo.spaces.remote

import nuvo.nio.prelude._
import nuvo.nio._
import nuvo.core.Tuple

object CreateSpaceHelper {

  def serialize(buf: RawBuffer, t: CreateSpace, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: CreateSpace) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(30)
    buf.put("nuvo.spaces.remote.CreateSpace".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.CreateSpace") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.CreateSpace")
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

  def serialize(buf: RawBuffer, t: LookupSpace, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: LookupSpace) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(30)
    buf.put("nuvo.spaces.remote.LookupSpace".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.LookupSpace") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.LookupSpace")
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

  def serialize(buf: RawBuffer, t: DeleteSpace, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: DeleteSpace) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(30)
    buf.put("nuvo.spaces.remote.DeleteSpace".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.DeleteSpace") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.DeleteSpace")
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

  def serialize(buf: RawBuffer, t: SpaceHash, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: SpaceHash) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(28)
    buf.put("nuvo.spaces.remote.SpaceHash".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.SpaceHash") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.SpaceHash")
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

  def serialize(buf: RawBuffer, t: ReadTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: ReadTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(28)
    buf.put("nuvo.spaces.remote.ReadTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.ReadTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.ReadTuple")
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

  def serialize(buf: RawBuffer, t: ReadAllTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: ReadAllTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(31)
    buf.put("nuvo.spaces.remote.ReadAllTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.ReadAllTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.ReadAllTuple")
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

  def serialize(buf: RawBuffer, t: TakeTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: TakeTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(28)
    buf.put("nuvo.spaces.remote.TakeTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.TakeTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.TakeTuple")
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

  def serialize(buf: RawBuffer, t: TakeAllTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: TakeAllTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(31)
    buf.put("nuvo.spaces.remote.TakeAllTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.TakeAllTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.TakeAllTuple")
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

  def serialize(buf: RawBuffer, t: SpaceTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: SpaceTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(29)
    buf.put("nuvo.spaces.remote.SpaceTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.SpaceTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.SpaceTuple")
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

  def serialize(buf: RawBuffer, t: NoMatchingTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: NoMatchingTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(34)
    buf.put("nuvo.spaces.remote.NoMatchingTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.NoMatchingTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.NoMatchingTuple")
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

  def serialize(buf: RawBuffer, t: SpaceTupleList, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: SpaceTupleList) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(33)
    buf.put("nuvo.spaces.remote.SpaceTupleList".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.SpaceTupleList") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.SpaceTupleList")
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

object OpenStreamHelper {

  def serialize(buf: RawBuffer, t: OpenStream, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: OpenStream) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(29)
    buf.put("nuvo.spaces.remote.OpenStream".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.OpenStream") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.OpenStream")
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

  def serialize(buf: RawBuffer, t: CloseStream, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: CloseStream) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(30)
    buf.put("nuvo.spaces.remote.CloseStream".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.CloseStream") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.CloseStream")
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

  def serialize(buf: RawBuffer, t: StreamCookie, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: StreamCookie) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(31)
    buf.put("nuvo.spaces.remote.StreamCookie".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.StreamCookie") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.StreamCookie")
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

  def serialize(buf: RawBuffer, t: StreamTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: StreamTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(30)
    buf.put("nuvo.spaces.remote.StreamTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.StreamTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.StreamTuple")
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

  def serialize(buf: RawBuffer, t: StreamTupleList, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: StreamTupleList) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(34)
    buf.put("nuvo.spaces.remote.StreamTupleList".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.StreamTupleList") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.StreamTupleList")
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

  def serialize(buf: RawBuffer, t: CompareAndSwap, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: CompareAndSwap) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(33)
    buf.put("nuvo.spaces.remote.CompareAndSwap".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.CompareAndSwap") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.CompareAndSwap")
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

  def serialize(buf: RawBuffer, t: WriteTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: WriteTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(29)
    buf.put("nuvo.spaces.remote.WriteTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.WriteTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.WriteTuple")
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

  def serialize(buf: RawBuffer, t: WriteTupleList, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: WriteTupleList) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(33)
    buf.put("nuvo.spaces.remote.WriteTupleList".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.WriteTupleList") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.WriteTupleList")
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

  def serialize(buf: RawBuffer, t: GetTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: GetTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(27)
    buf.put("nuvo.spaces.remote.GetTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.GetTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.GetTuple")
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

  def serialize(buf: RawBuffer, t: RemoveTuple, format: SerializationFormat) {
    format match {
    case NuvoSF  => serializeNuvoSF(buf, t)
    }
  }
   
  final def serializeNuvoSF(buf: RawBuffer, t: RemoveTuple) {
    buf.order(ByteOrder.nativeOrder)
    val __nuvoc_startPosition = buf.position
    buf.position(__nuvoc_startPosition + 4)
    buf.putInt(30)
    buf.put("nuvo.spaces.remote.RemoveTuple".getBytes())
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
    val __nuvoc_nameLen = buf.getInt()
    val __nuvoc_bbuf = new Array[Byte](__nuvoc_nameLen)
    buf.get(__nuvoc_bbuf)
    val __nuvoc_fqName = new String(__nuvoc_bbuf)
    if (__nuvoc_fqName != "nuvo.spaces.remote.RemoveTuple") throw new RuntimeException("Cannot deserialize  +__nuvoc_fqName+ as a nuvo.spaces.remote.RemoveTuple")
    val hash = buf.getInt()
    buf.position(__nuvoc_startPosition + __nuvoc_serializeDataLength)
    new RemoveTuple(hash )
  }
  
  def deserializeNoHeaderNuvoSF(buf: RawBuffer) : RemoveTuple = {
    val hash = buf.getInt()
    new RemoveTuple(hash )
  }
  
}

