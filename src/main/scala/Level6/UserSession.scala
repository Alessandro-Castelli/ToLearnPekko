package Level6

import Level6.ChatProtocol.*
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.{ActorRef, Behavior}

object UserSession {
  
  def apply(name:String, chatRoom: ActorRef[ChatRoomCommand]):Behavior[UserEvent] = {
    Behaviors.setup{
      context =>
        chatRoom ! JoinRoom(name, context.self)
        
        Behaviors.receiveMessage{
          case SystemNotice(msg) =>
            context.log.info(s"[Sistema $msg]")
            Behaviors.same
          case MessagePosted(usr,msg)=>
            context.log.info(s"[$usr] $msg")
            Behaviors.same
    }
        
    }
  }

}
