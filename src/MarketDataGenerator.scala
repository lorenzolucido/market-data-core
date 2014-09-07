/**
 * Created by lorenzo on 9/7/14.
 */

import scala.util.Random
import akka.actor.ActorSystem
import akka.stream.{Transformer, MaterializerSettings, FlowMaterializer}
import akka.stream.scaladsl.Flow
import scala.concurrent.duration._
import scala.collection.immutable.Seq


/**
 * Created by lorenzo on 9/6/14.
 */
object MarketDataGenerator extends App {
  implicit val sys = ActorSystem("Stream")
  implicit val ec = sys.dispatcher
  implicit val marketFlowMaterializer = FlowMaterializer(MaterializerSettings())

  def randomVariableFlow(interval: Int = 250): Flow[Float] =
    Flow ( Flow(100.milli, interval.milli, () => Random.nextFloat()).toPublisher )
  def randomVariable(action: Float => Unit) = randomVariableFlow().foreach(action)

  case class randomStock(symbol: String){
    //val vol = Random.nextDouble / 100
    val initPrice = Math.round(Random.nextFloat() * 100)
    //var currentPrice = initPrice
    //def tick = () =>
  }

  class randomStockPriceTransformer(symbol: String, initPrice: Float) extends Transformer[Float, (String, Float)] {
    var currentPrice = initPrice

    def onNext(element: Float): Seq[(String, Float)] = {
      currentPrice = currentPrice * (1 + element / 100)
      Seq((symbol, currentPrice))
    }
  }

  def randomStockFlow(symbol: String): Flow[(String, Float)] = {
    val initPrice: Float = 100 //Math.round(Random.nextFloat() * 100)
    val transf = new randomStockPriceTransformer(symbol, initPrice)
    val input =
    //randomVariableFlow().fold(symbol, initPrice)((s, f) => (symbol, s._2 * (1 + f / 100) ) ).toPublisher
      randomVariableFlow().transform(transf).toPublisher
    Flow(input)

  }

  def randomPrice = (initPrice: Float, vol: Float) => {
    //val
  }

  override val executionStart: Long = {
    //randomVariableFlow().take(10).foreach(println)
    randomStockFlow("AAPL").take(15).foreach(println)
    //.onComplete(_ => sys.shutdown() )



    0
  }

}
