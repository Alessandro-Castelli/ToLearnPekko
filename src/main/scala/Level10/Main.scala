package Level10

import org.apache.pekko.actor.typed.ActorSystem
import Level10.Messaggi._

/*
================================================================================
TEATRO CON PEKKO (AKKA TYPED) - DESCRIZIONE DEL PROGRAMMA
================================================================================

Questo programma implementa un sistema di prenotazioni teatrali utilizzando Pekko Typed Actors
e fornisce un server HTTP per interagire con esso.

STRUTTURA DEL PROGRAMMA:

1️⃣ TeatroAttore
   - Attore principale che gestisce il teatro.
   - Tiene una mappa di spettacoli (`Map[String, ActorRef[SpettacoloCommand]]`).
   - Gestisce i comandi:
       * CreaSpettacolo(nome, posti, replyTo) → crea un nuovo attore spettacolo.
       * ListaSpettacoli(replyTo) → restituisce la lista degli spettacoli disponibili.
       * Prenota(nomeSpettacolo, utente, replyTo) → inoltra la prenotazione all’attore dello spettacolo.
   - In pratica è il **gestore centrale del teatro**.

2️⃣ SpettacoloAttore
   - Attore che rappresenta un singolo spettacolo.
   - Mantiene lo stato dello spettacolo:
       * posti disponibili
       * lista prenotazioni
   - Gestisce i comandi:
       * PrenotaPosto(utente, replyTo) → prenota un posto se disponibile.
       * GetDettagli(replyTo) → restituisce dettagli dello spettacolo (posti disponibili e lista prenotazioni).
   - È responsabile della logica di ciascun spettacolo.

3️⃣ UtenteAttore
   - Attore che simula un utente che prenota uno spettacolo.
   - All’avvio invia al `TeatroAttore` il messaggio di prenotazione per un determinato spettacolo.
   - Riceve messaggi di conferma (`PrenotazioneEffettuata`) o errore (`Errore`) e li logga a console.

4️⃣ HttpServer
   - Server HTTP basato su Pekko HTTP.
   - Fornisce endpoint REST per interagire con il teatro:
       * GET /teatro/spettacoli → restituisce la lista degli spettacoli.
       * POST /teatro/spettacolo/{nome}/prenota/{utente} → prenota un posto.
       * GET /teatro/spettacolo/{nome}/dettagli → restituisce dettagli dello spettacolo.
   - Utilizza `ask pattern` per comunicare con l’`ActorSystem` in modo asincrono.
   - Gestisce il completamento delle Future e restituisce lo status HTTP corretto.

5️⃣ Main
   - Crea l’`ActorSystem` principale (`TeatroSystem`).
   - Crea alcuni spettacoli di esempio.
   - Crea alcuni utenti che prenotano automaticamente alcuni spettacoli.
   - Avvia il server HTTP su `http://localhost:8080`.

================================================================================
FLUSSO DEL PROGRAMMA:

1. Main crea l’ActorSystem del teatro.
2. Vengono creati gli attori spettacolo tramite `CreaSpettacolo`.
3. Vengono creati gli attori utenti, che subito inviano prenotazioni al teatro.
4. Il teatro inoltra le prenotazioni agli attori spettacolo.
5. Ogni attore spettacolo aggiorna il proprio stato e risponde agli utenti.
6. Il server HTTP permette di interagire con il teatro tramite REST.
7. Tutti i messaggi tra attori sono tipizzati e asincroni, garantendo sicurezza e concorrenza.

*/

object Main extends App {
  // Creo l'ActorSystem principale
  private val teatroSystem: ActorSystem[TeatroCommand] = ActorSystem(TeatroAttore(), "TeatroSystem")

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
  println("Server HTTP avviato. Premi un tasto per terminare.")
  scala.io.StdIn.readLine() 
}