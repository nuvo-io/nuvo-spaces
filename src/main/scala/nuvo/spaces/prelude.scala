package nuvo.spaces

import scala.language.implicitConversions
import nuvo.core.Tuple
import nuvo.spaces.local.LocalSpace
import java.util.concurrent.locks.{ReentrantLock}
import nuvo.concurrent.synchronizers._
import nuvo.spaces.remote.SpaceProxy

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

    def builderWithLocator[T <: Tuple](l: SpaceLocator): Option[Space[T]] = {
      Some(resolveSpace[T](l))
    }

    def builderWithOptLocator[T <: Tuple](l: Option[SpaceLocator]): Option[Space[T]] = {
      l map { loc => resolveSpace[T](loc) }
    }

  }

  object RemoteSpace {

    def builderWithLocator[T <: Tuple](l: SpaceLocator): Option[Space[T]] = l match {
      case rsl: RemoteSpaceLocator => Some(SpaceProxy[T](rsl))
      case lsl: LocalSpaceLocator => None
    }

    def builderWithOptLocator[T <: Tuple](l: Option[SpaceLocator]): Option[Space[T]] =
      l flatMap { loc => builderWithLocator[T](loc) }

  }


}
