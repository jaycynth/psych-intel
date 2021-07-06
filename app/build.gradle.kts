import com.google.protobuf.gradle.*

plugins {
    id("com.android.application")
    id("com.google.protobuf")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}


android {
    compileSdkVersion(Apps.compileSdk)
    buildToolsVersion = Apps.buildToolsVersion

    defaultConfig {
        applicationId = Apps.applicationId
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = Apps.versionCode
        versionName = Apps.versionName
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val serverUrl: String? by project
        if (serverUrl != null) {
            resValue("string", "server_url", serverUrl!!)
        } else {
            resValue("string", "server_url", "http://192.168.0.21:8080")
        }
    }
    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    api("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion") {
        exclude(group = "javax.annotation", module = "javax.annotation-api")
    }

    implementation(AppLibs.appLibraries)
    kapt(AppLibs.kaptLibraries)
    api(AppLibs.apiLibraries)
    runtimeOnly(AppLibs.runtimeLibraries)

    //test libs
    testImplementation(AppLibs.testLibraries)
    androidTestImplementation(AppLibs.androidTestLibraries)


}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }

    plugins {
        id("java") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
        id("grpckt") {
            artifact =
                "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("java") {
                    option("lite")
                }
                id("grpc") {
                    option("lite")
                }
                id("grpckt") {
                    option("lite")
                }
            }
        }
    }
}










