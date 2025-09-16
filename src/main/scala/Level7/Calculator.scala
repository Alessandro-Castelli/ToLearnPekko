package Level7

import Level7.Command.{CalculatorCommand, Diff, Div, Mul, Somma, Value}
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object Calculator {

  def apply(): Behavior[CalculatorCommand] = {
    Behaviors.receive(
      (context, command) =>
        command match {
          case Somma(x,y, replyTo) =>
            val res = x+y
            replyTo ! Value(res)
            Behaviors.same
          case Diff(x,y,replyTo) =>
            val res = x-y
            replyTo ! Value(res)
            Behaviors.same
          case Mul(x,y, replyTo) =>
            val res = x*y
            replyTo ! Value(res)
            Behaviors.same
          case Div(x,y, replyTo) =>
            val res = x/y
            replyTo ! Value(res)
            Behaviors.same
        }
    )
  }

}
