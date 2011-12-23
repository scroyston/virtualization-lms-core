import sbt._
import Keys._

object VirtLmsCoreBuild extends Build {
  // FIXME: custom-built scalatest
  val dropboxScalaTestRepo = "Dropbox" at "http://dl.dropbox.com/u/12870350/scala-virtualized"
  val scalatest = "org.scalatest" % "scalatest_2.10.0-virtualized-SNAPSHOT" % "1.6.1-SNAPSHOT" //% "test"

  val virtScala = "2.10.0-virtualized-SNAPSHOT"
  addCompilerPlugin("org.scala-lang.plugins" % "continuations" % virtScala)

  val virtBuildSettings = Defaults.defaultSettings ++ Seq(
    organization := "scala",
    version := "0.1-SNAPSHOT",
    scalaSource in Compile <<= baseDirectory(_ / "src"),
    resourceDirectory in Compile <<= baseDirectory(_ / "resources"),
    scalaSource in Test <<= baseDirectory(_ / "test-src"),
    resourceDirectory in Test <<= baseDirectory(_ / "test-resources"),
    resolvers += ScalaToolsSnapshots, 
    resolvers += dropboxScalaTestRepo,
    scalaVersion := virtScala,
    // needed for scala.tools, which is apparently not included in sbt's built in version
    libraryDependencies += scalatest,
    libraryDependencies += "org.scala-lang" % "scala-library" % virtScala,
    libraryDependencies += "org.scala-lang" % "scala-compiler" % virtScala,
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    scalacOptions in Test += "-P:continuations:enable"
  )

  // build targets
  lazy val root = Project("virtualization-lms-core", file("."), settings = virtBuildSettings)
}

