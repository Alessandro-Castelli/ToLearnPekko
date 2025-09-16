package Level7

import Level7.Command._
import org.apache.pekko.actor.typed.{ActorSystem, ActorRef, SupervisorStrategy}
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object Main extends App {

  // Creo l'ActorSystem principale, di tipo CalculatorCommand
  val system: ActorSystem[CalculatorCommand] =
    ActorSystem(Behaviors.setup[CalculatorCommand] { context =>

      // Creo l'attore Calculator supervisionato
      val supervisedCalculator: ActorRef[CalculatorCommand] =
        context.spawn(
          Behaviors
            .supervise(Calculator())
            .onFailure[ArithmeticException](SupervisorStrategy.restart),
          "Calculator"
        )

      // Creo il Client console
      context.spawn(Client(supervisedCalculator), "Client")

      // Il comportamento principale non riceve messaggi
      Behaviors.empty
    }, "CalculatorSystem")

}
