import play._
import sbt._
import com.typesafe.sbt.web.{SbtWeb, PathMapping}
import com.typesafe.sbt.web.pipeline.Pipeline
import sbt.Path.relativeTo
import sbt.File
import com.typesafe.sbt.web.Import.WebKeys
import play.Play.playPackageAssetsMappings
import com.typesafe.sbt.web.SbtWeb.deduplicateMappings

scalaVersion := "2.11.1"

name := "submode-example"

version := "1.0"

lazy val aggregateJs = taskKey[Pipeline.Stage]("aggregates web assets from submodules")

lazy val aggregateJsImpl = aggregateJs := { mappings: Seq[PathMapping] =>
  def getMappings(m: Seq[PathMapping], p: ResolvedProject): Seq[(File, String)] = {
    if (p.dependencies.isEmpty) {
      m
    } else {
      p.dependencies.map({ dep =>
        val subp = Project.getProjectForReference(dep.project, buildStructure.value).get
        val newFiles = (subp.base / "app" / "assets" / "js").***.get.toSet
        val newMappings: Seq[(File, String)] = newFiles pair relativeTo(subp.base / "app" / "assets")
        getMappings(m ++ newMappings, subp)
      }).flatten
    }
  }
  val newMappings = getMappings(mappings, thisProject.value).distinct
  SbtWeb.syncMappings(
    streams.value.cacheDirectory,
    newMappings,
    WebKeys.webTarget.value / aggregateJs.key.label
  )
  newMappings
}

libraryDependencies ++= Seq(
  "org.webjars" % "requirejs" % "2.1.11-1",
  "org.webjars" % "jquery" % "2.1.1"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(core)
  .aggregate(core)
  .settings(
    scalaVersion := "2.11.1",
    aggregateJsImpl,
    pipelineStages := Seq(aggregateJs, rjs),
    RjsKeys.webJarCdns := Map.empty, //Makes sure that webjar libs are built in and NOT served from a CDN
    playPackageAssetsMappings := deduplicateMappings(playPackageAssetsMappings.value, Seq(_.headOption)) //This workaround only 
  )

lazy val core = (project in file("modules/core"))
  .enablePlugins(PlayScala)
  .dependsOn(common)
  .aggregate(common)
  .settings(
    scalaVersion := "2.11.1",
    RjsKeys.webJarCdns := Map.empty, //Makes sure that webjar libs are built in and NOT served from a CDN
    playPackageAssetsMappings := deduplicateMappings(playPackageAssetsMappings.value, Seq(_.headOption)) //This workaround only 
  )

lazy val common = (project in file("modules/common"))
  .enablePlugins(PlayScala)
  .settings(
    scalaVersion := "2.11.1",
    RjsKeys.webJarCdns := Map.empty, //Makes sure that webjar libs are built in and NOT served from a CDN
    playPackageAssetsMappings := deduplicateMappings(playPackageAssetsMappings.value, Seq(_.headOption)) //This workaround only 
  )