package Level6

import Level2.Command
import Level6.ChatProtocol.{*}
import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors


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
            }

          case SendMessage(user, msg) =>
            val msgg = check(msg)
            context.log.info(s"user invia: $msgg")
            users.foreach{case(_,ref) => ref ! MessagePosted(user,msgg)}
            Behaviors.same

          case SendPrivateMessage(from, to, msg) =>
            users.get(to)match {
              case Some(ref) =>
                context.log.info(s"$from invia a  $to: $msg (privato)")
                users.get(from).foreach(_ ! SystemNotice(s"messaggio provato inviato a $to"))
              case None =>
                users.get(from).foreach(_ ! SystemNotice(s"$to non esiste"))
            }
            Behaviors.same
        }
    }
  }

  private def check(msg: String): String = {
    // Splitta il messaggio in parole, sostituisci le parole vietate
    msg
      .split(" ")
      .map(word => if (badWords.contains(word)) "***" else word)
      .mkString(" ")
  }


}
