package Level9

import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object Router {
  sealed trait Command
  case class Job(task: String) extends Command
  case class JobCompleted(done: Worker.JobDone) extends Command

  def apply(): Behavior[Command] = Behaviors.setup { ctx =>
    val workers = (1 to 3).map(i => ctx.spawn(Worker(i), s"worker-$i")).toVector

    // Adapter per trasformare Worker.JobDone -> Router.Command
    val jobDoneAdapter: ActorRef[Worker.JobDone] =
      ctx.messageAdapter(done => JobCompleted(done))

    def loop(next: Int): Behavior[Command] =
      Behaviors.receiveMessage {
        case Job(task) =>
          // invio al worker usando l'adapter
          workers(next) ! Worker.DoJob(task, jobDoneAdapter)
          loop((next + 1) % workers.size)

        case JobCompleted(done) =>
          ctx.log.info(s"Router riceve conferma da Worker-${done.workerId} per: ${done.task}")
          Behaviors.same
      }

    loop(0)
  }
}
