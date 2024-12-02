import sbt.Keys.*
import sbtassembly.AssemblyPlugin.autoImport.*
import sbtassembly.AssemblyPlugin.defaultShellScript

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.15"
ThisBuild / assemblyPrependShellScript := Some(defaultShellScript)

lazy val root = (project in file("."))
  .settings(
    name := "flights_loader",

    Compile / run / mainClass := Some("org.ntic.flights.FlightsLoaderApp"),
    Compile / packageBin / mainClass := Some("org.ntic.flights.FlightsLoaderApp"),

    assembly / mainClass := Some("org.ntic.flights.FlightsLoaderApp"),

    assembly / assemblyJarName := "flights_loader.jar",

    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.2",
      "org.scalatest" %% "scalatest" % "3.2.17" % Test,
      "org.scala-lang.modules" %% "scala-collection-compat" % "2.6.0",
      "com.typesafe" % "config" % "1.4.2"
    )
  )
