package Level4

import Level4.ChatProtocol.{JoinRoom, SystemNotice}
import org.apache.pekko.actor.typed.ActorSystem

/**
 * Main entry point for a simple broadcast ChatRoom using Pekko Typed.
 * This sets up the ChatRoom actor and two user sessions: Alice and Bob.
 */
object Main extends App {

  // Create the ActorSystem with ChatRoom as the root actor
  val system: ActorSystem[ChatProtocol.ChatRoomCommand] =
    ActorSystem(ChatRoom(), "ChatRoomSystem")

  // Create user session actors as children of the system
  val alice = system.systemActorOf(UserSession("Alice", system), "AliceSystem")
  val bob   = system.systemActorOf(UserSession("Bob", system), "BobSystem")

}
