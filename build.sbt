lazy val root = project.in( file("."))
                       .aggregate(server_core, server_finagle)

lazy val server_core = project.in( file("server/core"))

lazy val server_finagle = project.in( file("server/finagle"))
                                 .dependsOn( server_core % "compile->compile")

lazy val client_web = project.in( file("client/web"))

