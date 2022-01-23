scalaVersion := "3.1.0"

enablePlugins(ScalaNativePlugin)
enablePlugins(BuildInfoPlugin)

lazy val commonSettings = Seq(
  ThisBuild / scalaVersion := "3.1.0",
  name := "sq"
)

lazy val buildInfoSettings = Seq(
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
  buildInfoPackage := "sq"
)

lazy val nativeBuildSettings = Seq(
  nativeLinkStubs := true,
  Compile / mainClass := Some("sq.Main"),
  libraryDependencies ++= Seq(
    // "org.scala-lang.modules" % "scala-parser-combinators_native0.4_3" % "2.1.0",
    "org.scala-lang.modules" %%% "scala-parser-combinators" % "2.1.0",
    "com.github.scopt" %%% "scopt" % "4.0.1" // "3.6.0"
  )
)

lazy val sq = project.in(file("."))
  .settings(commonSettings)
  .settings(buildInfoSettings)
  .settings(nativeBuildSettings)
