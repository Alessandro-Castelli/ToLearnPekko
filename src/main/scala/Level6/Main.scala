package Level6

import Level6.ChatProtocol.{SendMessage, SendPrivateMessage}
import org.apache.pekko.actor.typed.ActorSystem

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.*


/**
 * Main entry point for a simple broadcast ChatRoom using Pekko Typed.
 * This sets up the ChatRoom actor and two user sessions: Alice and Bob. (NO BROADCAST, with bad words)
 */
object Main extends App {

  // Create the ActorSystem with ChatRoom as the root actor
  val system: ActorSystem[ChatProtocol.ChatRoomCommand] =
    ActorSystem(ChatRoom(), "ChatRoomSystem2")

  // Create user session actors as children of the system
  val alice = system.systemActorOf(UserSession("Alice", system), "AliceSystem")
  val bob   = system.systemActorOf(UserSession("Bob", system), "BobSystem")

  // Use a scheduler to delay the private message
  implicit val ec: ExecutionContext = system.executionContext

  system.scheduler.scheduleOnce(3.seconds, new Runnable {
    override def run(): Unit = {
      system ! SendPrivateMessage("Alice", "Bob", "Ciao Bob, sono Alice")
      system ! SendMessage("Alice", "Poste Italiane son mitiche")
    }
   }
  )
}
