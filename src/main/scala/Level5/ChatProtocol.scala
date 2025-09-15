package Level5

import org.apache.pekko.actor.typed.ActorRef

object ChatProtocol {
  
  sealed trait ChatRoomCommand
  case class JoinRoom(name:String, replyTo: ActorRef[UserEvent]) extends ChatRoomCommand
  case class SendMessage(user: String, msg: String) extends ChatRoomCommand
  case class SendPrivateMessage(from:String, to:String, msg: String) extends ChatRoomCommand
  
  sealed trait UserEvent
  case class MessagePosted(user: String, meg:String)extends UserEvent
  case class SystemNotice(msg: String) extends UserEvent
}
