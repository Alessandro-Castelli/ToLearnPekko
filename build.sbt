name := "ToLearnPekko"
version := "0.1.0"
scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "org.apache.pekko" %% "pekko-actor-typed" % "1.0.1",
      "org.apache.pekko" %% "pekko-cluster-typed" % "1.0.1",
      "org.apache.pekko" %% "pekko-serialization-jackson" % "1.0.1",
      "org.apache.pekko" %% "pekko-http-spray-json" % "1.0.1",
      "org.apache.pekko" %% "pekko-stream-typed" % "1.0.1",
      "org.apache.pekko" %% "pekko-http" % "1.0.1",
      "ch.qos.logback" % "logback-classic" % "1.4.11",
      "org.apache.pekko" %% "pekko-stream" % "1.0.1",

      "org.apache.pekko" %% "pekko-actor-testkit-typed" % "1.0.1" % Test,
      "org.scalatest" %% "scalatest" % "3.2.9" % Test
    )
  )

