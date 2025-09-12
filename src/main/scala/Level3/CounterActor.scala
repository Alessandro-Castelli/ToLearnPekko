package Level3

import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors


object CounterActor {
  
  def apply(count: Int = 0): Behavior[Command] = Behaviors.receiveMessage {
    case Increment =>
      println(s"Contatore: ${count + 1}")
      CounterActor(count + 1)
    case Decrement =>
      println(s"Contatore: ${count - 1}")
      CounterActor(count - 1)
  }
  
}
