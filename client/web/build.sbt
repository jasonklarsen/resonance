import Deps._

import Ports._

name := "resonance-web"

version := "0.1"

organization := "io.larsen"

scalaVersion := "2.10.3"

resolvers += "Twitter Repo" at "http://maven.twttr.com"

//libraryDependencies += "com.twitter" %% "finagle-http" % "6.2.0"
libraryDependencies += "com.twitter" % "finatra" % "1.4.0"

mainClass := Some("io.larsen.resonance.web.Server")

fork in run := true

//This is duplicate across builds.... Should pull the duplication into Ports.scala, but.... time.
javaOptions += ("-DwebPort=" + webPort)

javaOptions += ("-DresPort=" + resPort)
