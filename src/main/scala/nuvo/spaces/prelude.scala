package nuvo.spaces

import scala.language.implicitConversions
import nuvo.core.Tuple
import nuvo.spaces.local.LocalSpace
import java.util.concurrent.locks.{ReentrantLock}
import nuvo.concurrent.synchronizers._

package object prelude {
  object LocalSpace {

    val lock = new ReentrantLock()
    var localSpacesMap = Map[String, Space[_ <: Tuple]]()

    private def resolveSpace[T <: Tuple](l: SpaceLocator): Space[T] = {
      (localSpacesMap get(l.name)).map (s => s.asInstanceOf[Space[T]]).getOrElse {
        val s = new LocalSpace[T](l)

        syncrhonized(lock) {
          localSpacesMap = localSpacesMap + (l.name -> s)
        }
        s.asInstanceOf[Space[T]]
      }
    }

    implicit def localSpaceBuilderWithLocator[T <: Tuple](l: SpaceLocator): Option[Space[T]] = {
      Some(resolveSpace[T](l))
    }

    implicit def localSpaceBuilderWithOptLocator[T <: Tuple](l: Option[SpaceLocator]): Option[Space[T]] = {
      l map { loc => resolveSpace[T](loc) }
    }

  }


}
