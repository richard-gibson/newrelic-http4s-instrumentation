val Http4sVersion          = "0.21.24"
val CirceVersion           = "0.13.0"
val MunitVersion           = "0.7.20"
val LogbackVersion         = "1.2.3"
val MunitCatsEffectVersion = "0.13.0"
val newRelicVersion        = "7.2.0-SNAPSHOT"

lazy val newrelicAgentPath = "/opt/newrelic/newrelic.jar"
// lazy val snapShotRep = Resolver.mavenLocal
lazy val snapShotRep =
 "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "quickstart",
    version := "0.0.1-SNAPSHOT",
    resolvers += snapShotRep,
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      "com.newrelic.agent.java"  % "newrelic-api"            % newRelicVersion,
      "com.newrelic.agent.java" %% "newrelic-scala-cats-api" % newRelicVersion,
      "org.http4s"              %% "http4s-blaze-server"     % Http4sVersion,
      "org.http4s"              %% "http4s-blaze-client"     % Http4sVersion,
      "org.http4s"              %% "http4s-circe"            % Http4sVersion,
      "org.http4s"              %% "http4s-dsl"              % Http4sVersion,
      "io.circe"                %% "circe-generic"           % CirceVersion,
      "org.scalameta"           %% "munit"                   % MunitVersion           % Test,
      "org.typelevel"           %% "munit-cats-effect-2"     % MunitCatsEffectVersion % Test,
      "ch.qos.logback"           % "logback-classic"         % LogbackVersion,
      "org.scalameta"           %% "svm-subs"                % "20.2.0"
    ),
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.13.0" cross CrossVersion.full
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework")
  )

Compile / run / fork := true
run / javaOptions += s"-javaagent:$newrelicAgentPath"
run / javaOptions += s"-Dnewrelic.config.log_level=fine"
