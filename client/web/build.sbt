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

packageArchetype.java_application


//watchSources <+= sourceDirectory map { _ / "test" }

//Jasmine stuff
//seq(jasmineSettings : _*)

//appJsDir <+= sourceDirectory { src => src / "main" / "resources" / "public" / "js" }

//appJsLibDir <+= sourceDirectory { src => src / "main" / "resources" / "public" / "js" / "lib" }

//jasmineTestDir <+= sourceDirectory { src => src / "test" / "resources" / "public" / "js"  }

//jasmineConfFile <+= sourceDirectory { src => src / "test" / "resources" / "public" / "js" / "test-dependencies.js" }

//jasmineRequireJsFile <+= sourceDirectory { src => src / "main" / "resources" / "public" / "js" / "lib" / "require" / "require-2.0.6.js" }

//jasmineRequireConfFile <+= sourceDirectory { src => src / "test" / "resources" / "public" / "js" / "require.conf.js" }

//(test in Test) <<= (test in Test) dependsOn (jasmine)