import sbt._

object Dependencies {

  val scalatest = "org.scalatest" %% "scalatest" % "1.9.1"
  val junit = "junit" % "junit" % "4.11"

  val appDependencies = Seq(
    scalatest % "test",
    junit % "test"
  )

}