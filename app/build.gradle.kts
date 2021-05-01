import com.google.protobuf.gradle.*

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.protobuf")
    id("kotlin-kapt")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs")
}


android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"


    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val serverUrl: String? by project
        if (serverUrl != null) {
            resValue("string", "server_url", serverUrl!!)
        } else {
            resValue("string", "server_url", "http://192.168.5.160:8080")
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
}


repositories {
    mavenCentral()
}



dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.core:core-ktx:1.3.2")

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:${rootProject.ext["hiltVersion"]}")
    kapt ("com.google.dagger:hilt-compiler:${rootProject.ext["hiltVersion"]}")

    // by viewmodels delegate
    implementation("androidx.activity:activity-ktx:1.2.1")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

    implementation(kotlin("stdlib"))
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    api("com.google.protobuf:protobuf-javalite:${rootProject.ext["protobufVersion"]}")
    api("io.grpc:grpc-kotlin-stub-lite:${rootProject.ext["grpcKotlinVersion"]}")

    runtimeOnly("io.grpc:grpc-okhttp:${rootProject.ext["grpcVersion"]}")


}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.ext["protobufVersion"]}"
    }

    plugins {
        id("java") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.ext["grpcVersion"]}"
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.ext["grpcVersion"]}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${rootProject.ext["grpcKotlinVersion"]}:jdk7@jar"
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










