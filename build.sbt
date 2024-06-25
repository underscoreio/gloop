name         := "slickless"
organization := "io.underscore"
version      := "0.3.7-SNAPSHOT"
scalaVersion := "2.13.1"

crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1")

licenses += ("Apache-2.0", url("http://apache.org/licenses/LICENSE-2.0"))

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xfatal-warnings"
)

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick"           % "3.3.2",
  "com.chuusai"        %% "shapeless"       % "2.3.3",
  "org.scalatest"      %% "scalatest"       % "3.2.19"   % "test",
  "com.h2database"      % "h2"              % "1.4.200" % "test",
  "ch.qos.logback"      % "logback-classic" % "1.2.9"   % "test"
)

pomExtra in Global := {
  <url>https://github.com/underscoreio/slickless</url>
  <scm>
    <connection>scm:git:github.com/underscoreio/slickless</connection>
    <developerConnection>scm:git:git@github.com:underscoreio/slickless</developerConnection>
    <url>github.com/underscoreio/slickless</url>
  </scm>
  <developers>
    <developer>
      <id>d6y</id>
      <name>Richard Dallaway</name>
      <url>http://twitter.com/d6y</url>
    </developer>
    <developer>
      <id>davegurnell</id>
      <name>Dave Gurnell</name>
      <url>http://twitter.com/davegurnell</url>
    </developer>
    <developer>
      <id>milessabin</id>
      <name>Miles Sabin</name>
      <url>http://twitter.com/milessabin</url>
    </developer>
  </developers>
}

