package Level3

sealed trait Command
case object Increment extends Command
case object Decrement extends Command
