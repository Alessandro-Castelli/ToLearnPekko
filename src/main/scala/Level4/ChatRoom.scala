package Level4

import Level4.ChatProtocol.ChatRoomCommand
import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import ChatProtocol.*

object ChatRoom {
  
  def apply() : Behavior[ChatRoomCommand] = {
    chat(Map.empty)
  }
  
  private def chat(users: Map[String, ActorRef[UserEvent]]): Behavior[ChatRoomCommand] = {
    Behaviors.receive{
      (context, message) =>
        message match {
          case JoinRoom(name, replyTo) =>
            if(users.contains(name)) {
              replyTo ! SystemNotice(s"$name is used")
              Behaviors.same
            } else {
              context.log.info(s"$name si è unito alla chat")
              replyTo ! SystemNotice(s"benvenuto nella chat $name")
              chat(users + (name ->replyTo))
              Behaviors.same
            }

          case SendMessage(user, msg) => 
            context.log.info(s"user invia: $msg")
            users.foreach{case(_,ref) => ref ! MessagePosted(user,msg)}
            Behaviors.same
        }
    }
  }

}
