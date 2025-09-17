package Level8

import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object Router {

  def apply(): Behavior[Command] = Behaviors.setup { ctx =>
    val workers = (1 to 3).map(i => ctx.spawn(Worker(i), s"worker-$i")).toVector

    def loop(next: Int): Behavior[Command] =
      Behaviors.receiveMessage {
        msg =>
          msg match {
            case Job(task) =>
              workers(next) ! DoJob(task)
              loop((next + 1) % workers.size)
          }
      }
    loop(0)
  }
}