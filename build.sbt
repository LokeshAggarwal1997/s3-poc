name := "poc-s3"

version := "0.1"

scalaVersion := "2.12.8"

// https://mvnrepository.com/artifact/software.amazon.awssdk/s3
libraryDependencies += "software.amazon.awssdk" % "s3" % "2.7.14"



lazy val slf4jSimpleLogging: ModuleID = "org.slf4j" % "slf4j-simple" % "2.0.0-alpha0"
lazy val slf4jLogging: ModuleID = "org.slf4j" % "slf4j-api" % "2.0.0-alpha0"
