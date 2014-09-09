package events

/**
 * Created by lorenzo on 9/7/14.
 */
case class Quote(symbol: String, bidSize: Int, bid: Float, ask: Float, askSize: Int) extends MarketEvent {
  override def toString: String = " / Quote / [ " + bidSize + " - " + bid + " | " + ask + " - " + askSize + " ]"
}
