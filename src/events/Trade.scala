package events

import org.joda.time.DateTime

/**
 * Created by lorenzo on 9/7/14.
 */
class Trade(val symbol: String, val quantity: Int, val price: Float, val time: DateTime) extends MarketEvent {
  override def toString: String = "<< Trade >> [ " + time + " | " + symbol + ": " + quantity + " @ " + price + " ]"
}
