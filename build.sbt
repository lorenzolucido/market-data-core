name := "market-data-core"

version := "1.0"

scalaVersion := "2.11.2"

organization := "com.enzo"

// set the main Scala source directory to be <base>/src
scalaSource in Compile := baseDirectory.value / "src"

// set the Scala test source directory to be <base>/test
scalaSource in Test := baseDirectory.value / "test"


libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.0",
  "org.joda" % "joda-convert" % "1.5",
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test",
  "com.typesafe.akka" %% "akka-actor" % "2.3.4",
  "com.lmax" % "disruptor" % "3.2.0",
  "com.typesafe.akka" % "akka-testkit_2.11"  % "2.3.4",
  "com.typesafe.akka" %% "akka-stream-experimental" % "0.6"
)