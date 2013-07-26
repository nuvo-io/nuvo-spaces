package nuvo.spaces

import scala.language.implicitConversions
import nuvo.core.Tuple
import nuvo.spaces.local.LocalSpace
import java.util.concurrent.locks.{ReentrantReadWriteLock, ReentrantLock}
import nuvo.concurrent.synchronizers._
import nuvo.spaces.remote.{SpaceServer, SpaceProxy}

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
      case rsl: RemoteSpaceLocator => {
        for (spaceS <- SpaceServerRegistry.lookupSpaceServer(rsl.locator.toString());
             space <- spaceS.localCreateSpace[T](rsl.name)
        ) yield space
      }.orElse(Some(SpaceProxy[T](rsl)))

      case lsl: LocalSpaceLocator => None
    }

    def builderWithOptLocator[T <: Tuple](l: Option[SpaceLocator]): Option[Space[T]] =
      l flatMap { loc => builderWithLocator[T](loc) }

  }

  object SpaceServerRegistry {
    private val lock = new ReentrantReadWriteLock()
    private var serverRegistry = Map[String, SpaceServer]()

    def registerSpaceServer(locator: String, space: SpaceServer): Unit =  synchronizedWrite(lock) {
      serverRegistry = serverRegistry + (locator -> space)
    }

    def lookupSpaceServer(locator: String): Option[SpaceServer] = synchronizedRead(lock) {
      serverRegistry.get(locator)
    }

    def unregisterSpace(locator: String): Unit = synchronizedWrite(lock) {
      serverRegistry = serverRegistry - locator
    }

  }

}
