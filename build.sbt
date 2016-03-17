name := """IoT_data_curator"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

libraryDependencies ++= {
  Seq(
    "com.amazonaws" % "aws-java-sdk" % "1.10.52",
    "commons-configuration" % "commons-configuration" % "1.10",
    "com.github.levkhomich" %% "akka-tracing-play" % "0.4"
  )
}

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

