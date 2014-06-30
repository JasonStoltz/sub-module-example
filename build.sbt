import play._

scalaVersion := "2.11.1"

name := "submode-example"

version := "1.0"

libraryDependencies ++= Seq(
  "org.webjars" % "requirejs" % "2.1.11-1"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(core, common)
  .settings(
    scalaVersion := "2.11.1"
  )

lazy val core = (project in file("modules/core"))
  .enablePlugins(PlayScala)
  .dependsOn(common)
  .settings(
    scalaVersion := "2.11.1"
  )

lazy val common = (project in file("modules/common"))
  .enablePlugins(PlayScala)
  .settings(
    scalaVersion := "2.11.1"
  )

pipelineStages := Seq(rjs)