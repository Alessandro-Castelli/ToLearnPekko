package Level10

import Level10.Messaggi.*
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object SpettacoloAttore {

  def apply(nome: String, posti: Int) : Behavior[SpettacoloCommand] = {
    Behaviors.setup {
        ctx =>
          var postiDisponibili = posti
          var prenotazioni = List.empty[String]

          Behaviors.receiveMessage {
            case CancellaPosto(utente, replyTo) =>
              if(postiDisponibili > 0){
                postiDisponibili = postiDisponibili -1
                prenotazioni = prenotazioni.filterNot(_ == utente)
                replyTo ! PrenotazioneEliminata(nome, utente)
              }
              Behaviors.same
            case PrenotaPosto(utente, replyTo) =>
              if (postiDisponibili > 0) {
                prenotazioni = utente :: prenotazioni
                postiDisponibili = postiDisponibili - 1
                replyTo ! PrenotazioneEffettuata(nome, utente)
              } else {
                replyTo ! Errore(s"Nessun posto disponibile per '$nome'.")
              }
              Behaviors.same
            case GetDettagli(replyTo) =>
              replyTo ! DettagliRes(nome, postiDisponibili, prenotazioni)
              Behaviors.same

      }
    }
  }
}
