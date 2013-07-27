package nuvo.spaces

object Config {
  val spaceBufSize = 4196

  val builtInTypes = List(
  "nuvo.spaces.perf.Capsule"
  /*
    "nuvo.spaces.remote.CreateSpace",
    "nuvo.spaces.remote.LookupSpace",
    "nuvo.spaces.remote.DeleteSpace",
    "nuvo.spaces.remote.SpaceHash",
    "nuvo.spaces.remote.ReadTuple",
    "nuvo.spaces.remote.ReadAllTuple",
    "nuvo.spaces.remote.TakeTuple",
    "nuvo.spaces.remote.TakeAllTuple",
    "nuvo.spaces.remote.SpaceTuple",
    "nuvo.spaces.remote.NoMatchingTuple",
    "nuvo.spaces.remote.SpaceTupleList",
    "nuvo.spaces.remote.TListBegin",
    "nuvo.spaces.remote.TListEnd",
    "nuvo.spaces.remote.OpenStream",
    "nuvo.spaces.remote.CloseStream",
    "nuvo.spaces.remote.StreamCookie",
    "nuvo.spaces.remote.StreamTuple",
    "nuvo.spaces.remote.StreamTupleList",
    "nuvo.spaces.remote.CompareAndSwap",
    "nuvo.spaces.remote.WriteTuple",
    "nuvo.spaces.remote.WriteTupleList",
    "nuvo.spaces.remote.GetTuple",
    "nuvo.spaces.remote.RemoveTuple"
    */
    )

  def registerTypes(): Boolean = {
    val b = nuvo.spaces.remote.SpaceMessageTypeRegistration.registerTypes()
    builtInTypes.foreach(t => {
      println(s"Registering Types: $t")
      nuvo.nio.SerializerCache.registerType(t)
    })
    true
  }

  val typeRegistered = registerTypes()

}
