package Level9



import org.apache.pekko.actor.typed.ActorSystem
// With a possible Cluster
object Main extends App {

  // Controllo sicuro degli argomenti
  val port: String = if (args != null && args.nonEmpty) args(0) else "2551"

  val config = com.typesafe.config.ConfigFactory.parseString(s"""
    pekko.remote.artery.canonical.port = $port
    pekko.cluster.seed-nodes = ["pekko://ClusterSystem@127.0.0.1:2551"]
  """).withFallback(com.typesafe.config.ConfigFactory.load())

  val system = ActorSystem(Router(), "ClusterSystem", config)

  // Invio di job di esempio
  system ! Router.Job("Task 1")
  system ! Router.Job("Task 2")
  system ! Router.Job("Task 3")
}
