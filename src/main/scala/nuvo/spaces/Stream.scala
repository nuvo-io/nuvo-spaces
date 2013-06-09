package nuvo.spaces

import collection.mutable.ListBuffer
import nuvo.core.Tuple

trait Stream[T <: Tuple] extends (Tuple => Unit) {
  var observers: ListBuffer[T => Unit]
  def close(): Unit
}
