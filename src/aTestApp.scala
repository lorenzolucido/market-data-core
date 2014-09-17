import akka.actor.{Props, Actor, ActorSystem}
import org.joda.time.DateTime
import scala.concurrent.duration._


/**
 * Created by lorenzo on 9/13/14.
 */
object aTestApp extends App {
  println("I'm an App")
  val sys = ActorSystem("Stream")
  implicit val ec = sys.dispatcher

  // Defining a simple actor
  class PrintActor extends Actor {
    def receive = {
      case text: String => println(text + " | received at " + DateTime.now.toString("HH:mm:ss"))
    }
  }

  // Instantiating the actor
  val printActor = sys.actorOf(Props[PrintActor], "aPrintingActor")

  // Scheduling a message to be sent to the actor every 0.5 second
  sys.scheduler.schedule(0.seconds, 500.milli, printActor, "Hello, World !")

  // Stopping the system after X seconds
  sys.scheduler.scheduleOnce(
    2.seconds, new Runnable {
      def run(): Unit = {
        println("Time Elapsed... shutting down")
        sys.shutdown
      }
    })




}
