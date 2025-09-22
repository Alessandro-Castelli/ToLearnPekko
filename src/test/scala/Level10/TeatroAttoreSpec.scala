package Level10

import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.BeforeAndAfterAll
import org.apache.pekko.actor.testkit.typed.scaladsl.{ActorTestKit, TestProbe}
import org.apache.pekko.actor.typed.ActorRef

import Level10.Messaggi._

class TeatroAttoreSpec extends AnyWordSpecLike with BeforeAndAfterAll {

  val testKit = ActorTestKit()
  override def afterAll(): Unit = testKit.shutdownTestKit()

  "TeatroAttore" should {

    "creare uno spettacolo se non esiste già" in {
      val teatro = testKit.spawn(TeatroAttore())
      val probe = testKit.createTestProbe[TeatroResponse]()

      teatro ! CreaSpettacolo("Amleto", 100, probe.ref)
      probe.expectMessage(SpettacoloCreato("Amleto"))
    }

    "non creare due spettacoli con lo stesso nome" in {
      val teatro = testKit.spawn(TeatroAttore())
      val probe = testKit.createTestProbe[TeatroResponse]()

      teatro ! CreaSpettacolo("Otello", 50, probe.ref)
      probe.expectMessage(SpettacoloCreato("Otello"))

      teatro ! CreaSpettacolo("Otello", 50, probe.ref)
      probe.expectMessageType[Errore] // ci aspettiamo un errore
    }

    "restituire la lista degli spettacoli creati" in {
      val teatro = testKit.spawn(TeatroAttore())
      val probe = testKit.createTestProbe[TeatroResponse]()

      teatro ! CreaSpettacolo("Macbeth", 80, probe.ref)
      probe.expectMessage(SpettacoloCreato("Macbeth"))

      teatro ! ListaSpettacoli(probe.ref)
      val lista = probe.expectMessageType[ListaSpettacoliRes]
      assert(lista.spettacoli.contains("Macbeth"))
    }

    "rispondere con Errore se provo a prenotare uno spettacolo inesistente" in {
      val teatro = testKit.spawn(TeatroAttore())
      val probe = testKit.createTestProbe[TeatroResponse]()

      teatro ! Prenota("NonEsiste", "Mario", probe.ref)
      probe.expectMessageType[Errore]
    }
  }
}
