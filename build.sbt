name := "s3-test"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++=
  Seq("com.amazonaws"%"aws-java-sdk-s3"%"1.10.47",
  "com.amazonaws"%"aws-java-sdk-iam"%"1.10.20"
)

    