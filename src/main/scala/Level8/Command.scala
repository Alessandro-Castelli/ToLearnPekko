package Level8

sealed trait Command
case class DoJob(task: String) extends  Command
case class Job(task: String) extends Command