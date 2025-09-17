package Level8

import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object Worker {

  def apply(id: Int) : Behavior[Command] = {
    Behaviors.receive{
      (ctx, msg) =>
        msg match {
          case DoJob(task) =>
            ctx.log.info(s"Worker $id perform: $task")
            Behaviors.same
        }
    }
  }
}
