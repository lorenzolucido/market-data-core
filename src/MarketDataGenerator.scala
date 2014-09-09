

/**
 * Created by lorenzo on 9/7/14.
 */

import akka.stream.actor.ActorPublisher
import events.MarketEvent
import org.reactivestreams.{Subscriber, Subscription}
import scala.util.Random
import akka.actor.{Actor, ActorSystem}
import akka.stream.{Transformer, MaterializerSettings, FlowMaterializer}
import akka.stream.scaladsl.{Flow, Duct}
import scala.concurrent.duration._
import scala.collection.immutable


object MarketDataGenerator {

  lazy val sys = ActorSystem("Stream")
  lazy val ec = sys.dispatcher
  lazy val marketFlowMaterializer = FlowMaterializer(MaterializerSettings())(sys)

  class PriceEvent(val symbol: String, val price: Float) extends MarketEvent

  def randomVariableFlow(initialDelay: Int = 100, interval: Int = 250): Flow[Float] = {
    val randomPublisher = Flow(initialDelay milli, interval milli, () => Random.nextFloat()).toPublisher()(marketFlowMaterializer)

    Flow(randomPublisher)
  }

  def randomVariable(action: Float => Unit) = randomVariableFlow().foreach(action)(marketFlowMaterializer)

  /**
   *
   * Random StockEvent Transformer
   * Maps a random number to a random event on the stock (trade/quote)
   */
  class randToStockEvent(symbol: String, initPrice: Float = 100) extends Transformer[Float, MarketEvent] {
    var currentPrice = initPrice

    def onNext(element: Float): immutable.Seq[MarketEvent] = {
      currentPrice = currentPrice * (1 + element / 100)
      val priceEvent = new PriceEvent(symbol, currentPrice)
      immutable.Seq(priceEvent)
    }
  }


  def startMarketEventFlow(symbol: String): Flow[MarketEvent] = {
    val flowTransformer = new randToStockEvent(symbol, 100f)
    val input = randomVariableFlow()
      .transform(flowTransformer)
      .toPublisher()(marketFlowMaterializer)

    //Flow(input).take(15).foreach(println)(marketFlowMaterializer)
    Flow(input)
  }

  def startRandomStockFlow(symbol: String, action: MarketEvent => Unit): Unit = {
    val flowTransformer = new randToStockEvent(symbol, 100f)
    val input = randomVariableFlow()
      .transform(flowTransformer)
      .toPublisher()(marketFlowMaterializer)
    Flow(input).foreach(action)(marketFlowMaterializer)
  }
}
