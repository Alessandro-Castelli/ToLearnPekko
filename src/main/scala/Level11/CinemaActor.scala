package Level11
import scala.collection.mutable
import Level11.Command._
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object CinemaActor {

  def apply(): Behavior[CinemaCommand] = {
    Behaviors.setup{
      ctx =>
        val films = mutable.Map.empty[Int, String]
        
        Behaviors.receiveMessage{
          case AddFilm(nomeFilm, idFilm) =>
            films += (idFilm -> nomeFilm)
            ctx.log.info("Film aggiunto correttamente")
            Behaviors.same
          case GetFilm(replyTo) =>
            replyTo ! films.toMap()
            Behaviors.same            
        }
    }
  }
}
