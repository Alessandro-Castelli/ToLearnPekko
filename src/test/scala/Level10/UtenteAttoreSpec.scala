package Level10

import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.BeforeAndAfterAll
import org.apache.pekko.actor.testkit.typed.scaladsl.{ActorTestKit, TestProbe}
import org.apache.pekko.actor.typed.ActorRef
import Level10.Messaggi._

class UtenteAttoreSpec extends AnyWordSpecLike with BeforeAndAfterAll {

  val testKit = ActorTestKit()

  override def afterAll(): Unit = testKit.shutdownTestKit()

  "UtenteAttore" should {
    "inviare un messaggio Prenota al teatro all'avvio" in {
      // 1. Creo un TestProbe che fa da "teatro finto"
      val teatroProbe = testKit.createTestProbe[TeatroCommand]()

      // 2. Avvio l'attore Utente
      val utente: ActorRef[TeatroResponse] =
        testKit.spawn(UtenteAttore("Mario", teatroProbe.ref, "Amleto"))

      // 3. Controllo che il teatro finto abbia ricevuto un messaggio Prenota
      val prenotaMsg = teatroProbe.expectMessageType[Prenota]

      // 4. Faccio un paio di assert per essere sicuro
      assert(prenotaMsg.nomeSpettacolo == "Amleto")
      assert(prenotaMsg.utente == "Mario")
      assert(prenotaMsg.replyTo == utente)
    }
  }
}
