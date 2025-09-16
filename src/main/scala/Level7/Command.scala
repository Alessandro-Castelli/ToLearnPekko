package Level7

import org.apache.pekko.actor.typed.ActorRef

object Command {
  sealed trait CalculatorCommand
  case class Somma(x: Int, y:Int, replyTo: ActorRef[Result]) extends CalculatorCommand
  case class Diff(x: Int, y: Int, replyTo: ActorRef[Result]) extends CalculatorCommand
  case class Mul(x: Int, y: Int, replyTo: ActorRef[Result]) extends CalculatorCommand
  case class Div(x: Int, y: Int, replyTo: ActorRef[Result]) extends CalculatorCommand

  sealed trait Result
  case class Value(v: Int) extends Result
  case class Error(msg: String) extends Result

}
