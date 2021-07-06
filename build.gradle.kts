// Top-level build file where you can add configuration options common to all sub-projects/modules.
group = "com.julie"
version = "0.0.1-SNAPSHOT"

buildscript {

    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath(BuildPlugins.gradlePlugin)
        classpath(BuildPlugins.kotlinPlugin)
        classpath(BuildPlugins.protobufPlugin)
        classpath(BuildPlugins.daggerPlugin)
        classpath(BuildPlugins.navPlugin)
    }

}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://plugins.gradle.org/m2/") }

        mavenCentral()
        mavenLocal()
    }
}



tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}


