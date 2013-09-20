package urlshortener

import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class AppTest extends FunSpec {

  describe("My first spec") {

    it("should pass") {
      assert(true)
    }

  }

  describe("My second spec") {
    
    it("should have a pending test") (pending)

  }

}