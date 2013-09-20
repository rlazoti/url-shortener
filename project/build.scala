import sbt._
import sbt.Keys._
import Dependencies._
import com.twitter.sbt._

object AppBuilder extends Build {

  val appSettings = Seq(
    name := "url-shortener",
    organization := "com.rodrigolazoti",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.10.2",
    unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(Seq(_)),
    unmanagedSourceDirectories in Test <<= (scalaSource in Test)(Seq(_))
  )

  lazy val app = Project("url-shortener", file("."))
    .settings(appSettings : _*)
    .settings(GitProject.gitSettings : _*)
    .settings(BuildProperties.newSettings : _*)
    .settings(PackageDist.newSettings : _*)
    .settings(ReleaseManagement.newSettings : _*)
    .settings(VersionManagement.newSettings : _*)
    .settings(libraryDependencies ++= appDependencies)

}
