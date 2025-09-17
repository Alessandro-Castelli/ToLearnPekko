package Level10

import spray.json._
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import Level10.Messaggi._

object JsonFormats extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val dettagliResFormat: RootJsonFormat[DettagliRes] = jsonFormat3(DettagliRes)
  implicit val listaSpettacoliFormat: RootJsonFormat[ListaSpettacoliRes] = jsonFormat1(ListaSpettacoliRes)
  implicit val prenotazioneFormat: RootJsonFormat[PrenotazioneEffettuata] = jsonFormat2(PrenotazioneEffettuata)
  implicit val erroreFormat: RootJsonFormat[Errore] = jsonFormat1(Errore)
}
