package nuvo.spaces

import nuvo.net.Locator


object SpaceLocator {
  def apply(sl: String): Option[SpaceLocator] = {
    if (sl.contains("@")) {
      val parts = sl split("@")
      val locator = Locator(parts(1))

      locator.map(new RemoteSpaceLocator(parts(0), _))
    }
    else Some(LocalSpaceLocator(sl))
  }
}

abstract class SpaceLocator(val name: String)

case class LocalSpaceLocator(override val name: String)  extends SpaceLocator(name)

case class RemoteSpaceLocator(override val name: String, locator: Locator) extends SpaceLocator(name) {
  override def toString = s"$name@$locator"
}

