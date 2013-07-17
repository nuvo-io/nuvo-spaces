package nuvo.spaces.local

import collection.mutable.ListBuffer
import nuvo.spaces.{Stream}
import nuvo.core.Tuple


private [local] class LocalStream[T <: Tuple](val closeAction: () => Unit) extends Stream[T] {
  var observers = new ListBuffer[T => Unit]

  def close() = closeAction()
}
