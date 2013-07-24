package nuvo.demo

import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global

import nuvo.spaces._
import nuvo.spaces.prelude.LocalSpace._
import nuvo.core.Tuple

case class Oximeter(id: String, spO2: Int, bps: Int) extends Tuple {
  lazy val key = id
}

object SpaceDemo {

  def main(args: Array[String]) {
    val locator = SpaceLocator("OximeterSpace")
     val ospace = Space[Oximeter](locator)

    ospace map { space =>
      future {
        Thread.sleep(5000)

        space.write(Oximeter("Oxi-1", 100, 62))
      }

      println("about to do a synch read")

    val p: Tuple => Boolean = {
      case Oximeter("Oxi-1", _, _) => true
      case _ => false
    }

      val t = space.sread[Oximeter] (p)
      t map {o => {
        println(s"Tuple: $t")
      }}
      space.close()
    }
  }
}
