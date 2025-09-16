package Level7


import Level7.Command._
import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors

import scala.io.StdIn

object Client {

  def apply(calculator: ActorRef[CalculatorCommand]): Behavior[Result] =
    Behaviors.setup { context =>
      // Thread separato per leggere input
      new Thread(() => {
        while (true) {
          val line = StdIn.readLine("Inserisci comando (es: 2 + 3): ")
          line.split(" ").toList match {
            case x :: "+" :: y :: Nil =>
              calculator ! Somma(x.toInt, y.toInt, context.self)
            case x :: "-" :: y :: Nil =>
              calculator ! Diff(x.toInt, y.toInt, context.self)
            case x :: "*" :: y :: Nil =>
              calculator ! Mul(x.toInt, y.toInt, context.self)
            case x :: "/" :: y :: Nil =>
              calculator ! Div(x.toInt, y.toInt, context.self)
            case _ =>
              println("Comando non valido!")
          }
        }
      }).start()

      Behaviors.receiveMessage {
        case Value(v) =>
          println(s"Risultato: $v")
          Behaviors.same
        case Error(msg) =>
          println(s"Errore: $msg")
          Behaviors.same
      }
    }
}
