package nuvo.spaces.local

import collection.mutable.ListBuffer
import nuvo.spaces.{Stream}
import nuvo.core.Tuple


class LocalStream[T <: Tuple] extends Stream[T] {
  var observers = new ListBuffer[T => Unit]

  def close() { }

  def apply(v: Tuple) = observers.foreach(_(v.asInstanceOf[T]))

}
