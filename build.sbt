scalaVersion := "3.1.1"

enablePlugins(ScalaNativePlugin)
enablePlugins(BuildInfoPlugin)

lazy val commonSettings = Seq(
  ThisBuild / scalaVersion := "3.1.1",
  name := "sq"
)

lazy val buildInfoSettings = Seq(
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
  buildInfoPackage := "com.github.larsjaas.sq"
)

lazy val nativeBuildSettings = Seq(
  nativeLinkStubs := true,
  Compile / mainClass := Some("com.github.larsjaas.sq.Main"),
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %%% "scala-parser-combinators" % "2.1.1",
    "com.github.scopt" %%% "scopt" % "4.0.1"
  )
)

lazy val sq = project.in(file("."))
  .settings(commonSettings)
  .settings(buildInfoSettings)
  .settings(nativeBuildSettings)
