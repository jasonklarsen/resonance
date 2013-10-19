import sbt._
import Keys._

object Ports {
  val webPort = "8080"
  val resPort = "8081"

  javaOptions += "-DwebPort=" + webPort
  javaOptions += "-DresPort=" + resPort
}