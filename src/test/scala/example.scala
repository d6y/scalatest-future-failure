import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.Future

class ExampleSpec extends FlatSpec with Matchers with ScalaFutures {

  sealed trait Boom extends Throwable
  final case class ExampleFail() extends Boom

  def example: Future[Int] = Future.failed(ExampleFail())

  // Three tricks here:
  // 1. Using whenReady to await for the future
  // 2. Using .failed to access the failed exception
  // 3. If there's no failure, scalatest will catch NoSuchElement exception from the Future
  "A future" should "be able to fail" in {
    whenReady(example.failed) { e =>
      e shouldBe an [ExampleFail]
    }
  }
}
