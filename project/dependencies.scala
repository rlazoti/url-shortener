import sbt._

object Dependencies {

  val finagleVersion = "6.5.1"

  val finagleCore = "com.twitter" %% "finagle-core" % finagleVersion
  val finagleHttp = "com.twitter" %% "finagle-http" % finagleVersion
  val finagleRedis = "com.twitter" %% "finagle-redis" % finagleVersion
  val finagleOstrich4 = "com.twitter" %% "finagle-ostrich4" % finagleVersion
  val scalatest = "org.scalatest" %% "scalatest" % "1.9.1"
  val junit = "junit" % "junit" % "4.11"

  val appDependencies = Seq(
    finagleCore,
    finagleHttp,
    finagleRedis,
    finagleOstrich4,
    scalatest % "test",
    junit % "test"
  )

}