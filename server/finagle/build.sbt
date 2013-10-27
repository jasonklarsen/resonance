import Deps._

import Ports._

name := "resonance-finagle"

version := "0.1"

organization := "io.larsen"

scalaVersion := "2.10.3"

mainClass := Some("io.larsen.resonance.finagle.Server")

fork in run := true

javaOptions += ("-DwebPort=" + webPort)

javaOptions += ("-DresPort=" + resPort)

libraryDependencies ++= Seq(finagleHttp)

packageArchetype.java_application