package Level1

import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

/**
 * Create a Simple Actor
 * GOAL: Understand how to create an Actor and send it a message.
 *
 * This actor will print "Hello World" when it receives a message.
 */

object HelloWorld {
  def apply(): Behavior[String] =
    Behaviors.receiveMessage { message =>
      println(s"Message is: $message")
      Behaviors.same
    }
}
