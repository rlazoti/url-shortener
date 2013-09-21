import sbt._

object Dependencies {

  val finagleVersion = "6.5.1"

  val finagleCore = "com.twitter" %% "finagle-core" % finagleVersion
  val finagleHttp = "com.twitter" %% "finagle-http" % finagleVersion
  val finagleRedis = "com.twitter" %% "finagle-redis" % finagleVersion
  val finagleOstrich4 = "com.twitter" %% "finagle-ostrich4" % finagleVersion
  val commonsLang = "commons-lang" % "commons-lang" % "2.6"
  val scalatest = "org.scalatest" %% "scalatest" % "1.9.1"
  val junit = "junit" % "junit" % "4.11"
  val guava = "com.google.guava" % "guava" % "14.0.1"

  val appDependencies = Seq(
    finagleCore,
    finagleHttp,
    finagleRedis,
    finagleOstrich4,
    commonsLang,
    guava,
    scalatest % "test",
    junit % "test"
  )

}