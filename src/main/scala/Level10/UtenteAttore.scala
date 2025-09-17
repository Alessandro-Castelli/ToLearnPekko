package Level10


import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import Level10.Messaggi._

object UtenteAttore {
  def apply(nome: String, teatro: ActorRef[TeatroCommand], nomeSpettacolo: String): Behavior[TeatroResponse] = Behaviors.setup { context =>

    // All'avvio, l'utente chiede di prenotare un biglietto per un certo spettacolo
    teatro ! Prenota(nomeSpettacolo, nome, context.self)

    Behaviors.receiveMessage {
      case PrenotazioneEffettuata(spettacolo, utente) =>
        context.log.info(s"✅ Prenotazione riuscita per $utente allo spettacolo $spettacolo")
        Behaviors.same

      case Errore(msg) =>
        context.log.warn(s"⚠️ Errore per $nome: $msg")
        Behaviors.same

      case _ =>
        Behaviors.unhandled
    }
  }
}
