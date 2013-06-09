name		:= "nuvo-spaces"

version		:= "0.1.1"

organization 	:= "io.nuvo"

homepage :=  Some(new java.net.URL("http://nuvo.io"))

scalaVersion 	:= "2.10.1"

seq(githubRepoSettings: _*)

scalaSource in Compile <<= baseDirectory(_ / "src")

localRepo := Path.userHome / "github" / "repo"

githubRepo := "git@github.com:nuvo-io/mvn-repo.git"

// libraryDependencies += "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"

resolvers += "nuvo.io maven repo" at "http://nuvo-io.github.com/mvn-repo/releases"

libraryDependencies += "io.nuvo" % "nuvo-core_2.10" % "0.1.1"

autoCompilerPlugins := true

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

scalacOptions += "-optimise"

scalacOptions += "-feature"

scalacOptions += "-language:postfixOps"

//scalacOptions += "-Yinline-warnings"


proguardSettings

ProguardKeys.options in Proguard += """
-dontnote
-dontwarn
-dontoptimize
-ignorewarnings
-dontobfuscate
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keeppackagenames **
-keep  class nuvo.spcaes.remote.*Helper {
       *; 
}
-keep class scala.collection.immutable.Range {
        *;
}
-keepclassmembers class * { ** MODULE$; }
-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinPool {
  long ctl;
}
-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinPool$WorkQueue {
  int runState;
}
-keepclassmembernames class scala.concurrent.forkjoin.LinkedTransferQueue {
  scala.concurrent.forkjoin.LinkedTransferQueue$Node head;
  scala.concurrent.forkjoin.LinkedTransferQueue$Node tail;
  int sweepVotes;
}
-keepclassmembernames class scala.concurrent.forkjoin.LinkedTransferQueue$Node {
  java.lang.Object item;
  scala.concurrent.forkjoin.LinkedTransferQueue$Node next;
  java.lang.Thread waiter;
}
-dontnote scala.xml.**
-dontnote scala.concurrent.forkjoin.ForkJoinPool
-dontwarn scala.**
"""

// -optimizationpasses 3
//   -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable

ProguardKeys.options in Proguard += ProguardOptions.keepMain("nuvo.spaces.remote.SpaceServer")



