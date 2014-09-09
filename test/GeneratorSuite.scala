
import akka.actor.{Props, ActorSystem}
import akka.testkit._

import scala.concurrent.duration._
import org.scalatest._
import org.specs2.mutable._


class GeneratorSuite extends Specification {
  val system = MarketDataGenerator.sys
  val mat = MarketDataGenerator.marketFlowMaterializer

  "The RandomMarketDataGenerator" should {
    val testActor = TestProbe()(system)

    "generate some random stock flow" in {
      val symbol = "AAPL"
      val numElements = 3

      MarketDataGenerator.startMarketEventFlow(symbol)
        .take(numElements)
        .foreach( msg => testActor.ref ! msg.symbol )(mat)


     util.Try(1 to numElements foreach { s => testActor.expectMsg(1500.milli, symbol) }) must beSuccessfulTry
    }

  }


}
