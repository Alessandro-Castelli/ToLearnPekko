package Level10

import org.apache.pekko.actor.typed.ActorRef

object Messaggi {

  // ----- Messaggi per il Teatro -----
  sealed trait TeatroCommand
  case class CreaSpettacolo(nome: String, posti: Int, replyTo: ActorRef[TeatroResponse]) extends TeatroCommand
  case class ListaSpettacoli(replyTo: ActorRef[TeatroResponse]) extends TeatroCommand
  case class Prenota(nomeSpettacolo: String, utente: String, replyTo: ActorRef[TeatroResponse]) extends TeatroCommand
  case class CancellaPrenotazione(nomeSpettacolo: String, utente: String, replyTo: ActorRef[TeatroResponse]) extends TeatroCommand
  case class DettagliSpettacolo(nomeSpettacolo: String, replyTo: ActorRef[TeatroResponse]) extends TeatroCommand

  // ----- Risposte del Teatro -----
  sealed trait TeatroResponse
  case class SpettacoloCreato(nome: String) extends TeatroResponse
  case class ListaSpettacoliRes(spettacoli: List[String]) extends TeatroResponse
  case class PrenotazioneEffettuata(spettacolo: String, utente: String) extends TeatroResponse
  case class PrenotazioneEliminata(spettacolo: String, utente: String) extends TeatroResponse
  case class DettagliRes(nome: String, postiDisponibili: Int, prenotazioni: List[String]) extends TeatroResponse
  case class Errore(messaggio: String) extends TeatroResponse

  // ----- Messaggi per lo Spettacolo -----
  sealed trait SpettacoloCommand
  case class PrenotaPosto(utente: String, replyTo: ActorRef[TeatroResponse]) extends SpettacoloCommand
  case class CancellaPosto(utente: String, replyTo: ActorRef[TeatroResponse]) extends SpettacoloCommand
  case class GetDettagli(replyTo: ActorRef[TeatroResponse]) extends SpettacoloCommand
}
