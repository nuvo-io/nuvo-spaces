package nuvo.spaces

import collection.mutable.ListBuffer
import nuvo.core.Tuple

trait Stream[T <: Tuple] extends (Tuple => Unit) {
  var observers: ListBuffer[T => Unit]

  def close()

  def apply(v1: Tuple) {
    observers foreach (_(v1.asInstanceOf[T]) )
  }

}
