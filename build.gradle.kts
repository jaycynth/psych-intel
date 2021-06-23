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
        classpath(BuildPlugins.daggerPlugin)
        classpath(BuildPlugins.navPlugin)
        classpath(BuildPlugins.protobufPlugin)
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

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}



repositories {
    google()
    jcenter()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
    mavenCentral()
    mavenLocal()
}