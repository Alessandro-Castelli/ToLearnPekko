package Level3

import org.apache.pekko.actor.typed.ActorSystem
/**
 * Goal: Manage internal state within the actor.
 *
 * Task:
 * Create an actor that maintains a counter and increments it 
 * each time it receives an Increment message. Print the updated value.
 */

object Main extends App{
  val system = ActorSystem(CounterActor(), "count")
  
  system ! Increment
  system ! Increment
}
