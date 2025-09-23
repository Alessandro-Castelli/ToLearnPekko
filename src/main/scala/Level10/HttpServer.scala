package Level10

import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.server.Directives._ // Contiene tutti i costrutti per definire Route, tipo get, post, path ecc
import org.apache.pekko.http.scaladsl.model.StatusCodes // Codici HTTP
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.AskPattern._
import scala.concurrent.duration._
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContextExecutor
import Level10.Messaggi._
import JsonFormats._

object HttpServer {

  def start(teatro: ActorSystem[TeatroCommand]): Unit = {
    implicit val system: ActorSystem[Nothing] = teatro
    implicit val ec: ExecutionContextExecutor = system.executionContext
    implicit val timeout: org.apache.pekko.util.Timeout = 3.seconds

    val route =
      pathPrefix("teatro") {
        concat(
          path("spettacoli") {
            get {
              val fut = teatro ? (replyTo => ListaSpettacoli(replyTo))
              onComplete(fut) {
                case Success(res: ListaSpettacoliRes) => complete(res)
                case Success(err: Errore) => complete(StatusCodes.BadRequest, err)
                case _ => complete(StatusCodes.InternalServerError)
              }
            }
          },
          path("spettacolo" / Segment / "prenota" / Segment) { (nome, utente) =>
            post {
              val fut = teatro.ask[TeatroResponse](replyTo => Prenota(nome, utente, replyTo))
              onComplete(fut) {
                case Success(res: PrenotazioneEffettuata) => complete(res)
                case Success(err: Errore) => complete(StatusCodes.BadRequest, err)
                case _ => complete(StatusCodes.InternalServerError)
              }
            }
          },
          path("spettacolo" / Segment / "dettagli") { nome =>
            get {
              val fut = teatro.ask[TeatroResponse](replyTo => DettagliSpettacolo(nome, replyTo))
              onComplete(fut) {
                case Success(res: DettagliRes) => complete(res)
                case Success(err: Errore) => complete(StatusCodes.BadRequest, err)
                case _ => complete(StatusCodes.InternalServerError)
              }
            }
          }
        )
      }

    Http().newServerAt("localhost", 8080).bind(route)
    println("Server HTTP avviato su http://localhost:8080")
  }
}
