package Level8

import org.apache.pekko.actor.typed.ActorSystem

object Main extends App {
  val system = ActorSystem(Router(), "RouterSystem")

  system ! Job("Compito A")
  system ! Job("Compito B")
  system ! Job("Compito C")
  system ! Job("Compito D")
}
