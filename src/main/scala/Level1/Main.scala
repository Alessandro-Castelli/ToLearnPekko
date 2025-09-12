package Level1

import org.apache.pekko.actor.typed.ActorSystem

object Main extends App {
  val system: ActorSystem[String] = ActorSystem(HelloWorld.apply(), "Hello")
  system ! "Ciao, Pekko!"
  Thread.sleep(500)
  system.terminate()
}
