package Level9


import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object Worker {
  sealed trait Command
  case class DoJob(task: String, replyTo: ActorRef[JobDone]) extends Command
  case class JobDone(workerId: Int, task: String)

  def apply(id: Int): Behavior[Command] = Behaviors.receive { (ctx, msg) =>
    msg match {
      case DoJob(task, replyTo) =>
        ctx.log.info(s"Worker-$id esegue: $task")
        replyTo ! JobDone(id, task)
        Behaviors.same
    }
  }
}