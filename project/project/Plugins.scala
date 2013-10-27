import sbt._

object Plugins extends Build {
  lazy val plugins = Project("root", file("."))
    .dependsOn(file("lib/sbt-jasmine-plugin"))
}
