package io.larsen.resonance

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class CoreSpec extends FunSpec with ShouldMatchers {

  describe("A trivial method that confirms connectivity") {

    it("returns a datetime that is not after now") {
      val out = Core.sysTime
      val after = out.after(new java.util.Date())
      after should be (false)
    }
  }
}