package Level10

import Level10.Messaggi.*
import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object TeatroAttore {

  def apply() : Behavior[TeatroCommand] = {
    Behaviors.setup{
      ctx =>
        var spettacoli = Map.empty[String, ActorRef[SpettacoloCommand]]


        Behaviors.receiveMessage{
        case CreaSpettacolo(nome, posti, replyTo) =>
          if(spettacoli.contains(nome)) {
              replyTo ! Errore(s"Lo spettacolo $nome esiste già.")
          } else {
              val spettacoloActor = ctx.spawn(SpettacoloAttore(nome,posti), s"spettacolo_$nome")
            spettacoli = spettacoli + (nome -> spettacoloActor)

            replyTo ! SpettacoloCreato(nome)
          }
          
          Behaviors.same

        case ListaSpettacoli(replyTo) =>
          replyTo ! ListaSpettacoliRes(spettacoli.keys.toList)
          Behaviors.same

        case Prenota(nomeSpettacolo, utente, replyTo) =>
          spettacoli.get(nomeSpettacolo) match {
            case Some(spettacoloRef) =>
              spettacoloRef ! PrenotaPosto(utente, replyTo)
            case None =>
              replyTo ! Errore(s"Spettacolo '$nomeSpettacolo' non trovato.")  
          }
          Behaviors.same

        case CancellaPrenotazione(nomeSpettacolo, utente, replyTo) =>
          spettacoli.get(nomeSpettacolo) match {
            case Some(spettacoloRef) =>
              spettacoloRef ! CancellaPosto(utente, replyTo)
          }
          Behaviors.same
          
        case DettagliSpettacolo(nomeSpettacolo, replyTo) =>
          spettacoli.get(nomeSpettacolo) match {
            case Some(spettacoloRef) => spettacoloRef ! GetDettagli(replyTo)
            case None => replyTo ! Errore(s"Spettacolo '$nomeSpettacolo' non trovato.")
          }
          Behaviors.same  
      }
    }
  }
}
