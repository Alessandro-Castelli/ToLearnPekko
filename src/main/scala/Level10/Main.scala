package Level10

import org.apache.pekko.actor.typed.ActorSystem
import Level10.Messaggi._

object Main extends App {
  // Creo l'ActorSystem principale
  val teatroSystem: ActorSystem[TeatroCommand] = ActorSystem(TeatroAttore(), "TeatroSystem")

  // Creo alcuni spettacoli di esempio
  teatroSystem ! CreaSpettacolo("Amleto", 5, teatroSystem.ignoreRef)
  teatroSystem ! CreaSpettacolo("Otello", 3, teatroSystem.ignoreRef)
  teatroSystem ! CreaSpettacolo("Macbeth", 4, teatroSystem.ignoreRef)

  // Creo alcuni utenti che prenotano automaticamente
  val utenteAlice = teatroSystem.systemActorOf(UtenteAttore("Alice", teatroSystem, "Amleto"), "utente-Alice")
  val utenteBob   = teatroSystem.systemActorOf(UtenteAttore("Bob", teatroSystem, "Otello"), "utente-Bob")
  val utenteCarol = teatroSystem.systemActorOf(UtenteAttore("Carol", teatroSystem, "Macbeth"), "utente-Carol")

  // Avvio il server HTTP
  HttpServer.start(teatroSystem)
}
