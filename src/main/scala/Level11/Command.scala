package Level11

import org.apache.pekko.actor.typed.ActorRef
import Level11.CinemaActor

object Command {

  sealed trait CinemaCommand
  case class AddFilm(nomeFilm: String, idFilm: Int) extends CinemaCommand
  case class GetFilm(replyTo: ActorRef[Map[Int, String]]) extends CinemaCommand

}
