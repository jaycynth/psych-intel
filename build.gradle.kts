// Top-level build file where you can add configuration options common to all sub-projects/modules.
group = "com.julie"
version = "0.0.1-SNAPSHOT"

buildscript {

    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath ("com.google.protobuf:protobuf-gradle-plugin:0.8.15")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.33-beta")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.3")

    }

}

ext["hiltVersion"] = "2.33-beta"
ext["grpcVersion"] = "1.34.0"
ext["grpcKotlinVersion"] = "0.2.1"
ext["protobufVersion"] = "3.14.0"


allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
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


