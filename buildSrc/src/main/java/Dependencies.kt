import org.gradle.api.artifacts.dsl.DependencyHandler

object Apps {
    const val buildToolsVersion = "30.0.3"
    const val compileSdk = 30
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val applicationId = "com.julie.psych_intel"
}

private const val navVersion = "2.3.3"
private const val coroutineVersion = "1.4.1"
private const val hiltVersion = "2.28.1-alpha"
private const val lifecycleVersion = "2.2.0"
private const val hiltViewModelVersion = "1.0.0-alpha02"

const val grpcVersion = "1.34.0"
const val grpcKotlinVersion = "0.2.1"
const val protobufVersion = "3.14.0"


object BuildPlugins {
    const val gradlePlugin = "com.android.tools.build:gradle:4.1.2"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72"
    const val daggerPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    const val navPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"
    const val protobufPlugin = "com.google.protobuf:protobuf-gradle-plugin:0.8.15"
}


object AppLibs {
    private const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    private const val materialUI = "com.google.android.material:material:1.3.0"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    private const val androidXlegacy = "androidx.legacy:legacy-support-v4:1.0.0"
    private const val androidXcore = "androidx.core:core-ktx:1.3.2"


    private const val navFrag = "androidx.navigation:navigation-fragment-ktx:$navVersion"
    private const val navUI = "androidx.navigation:navigation-ui-ktx:$navVersion"

    private const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
    private const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"


    private const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
    private const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    private const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltViewModelVersion"
    private const val hiltCompiler = "androidx.hilt:hilt-compiler:$hiltViewModelVersion"


    private const val viewmodelLifecycle =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    private const val livedataLifecycle =
        "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    private const val extensionLifecycle = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    private const val fragmentLifecycle = "androidx.fragment:fragment-ktx:1.2.4"

    //grpc
    private const val javaxAnnotation = "javax.annotation:javax.annotation-api:1.3.2"
    private const val javalite = "com.google.protobuf:protobuf-javalite:${protobufVersion}"
    private const val stublite = "io.grpc:grpc-kotlin-stub-lite:${grpcKotlinVersion}"
    private const val grpcOkhttp = "io.grpc:grpc-okhttp:${grpcVersion}"


    val kaptLibraries = arrayListOf<String>().apply {
        add(hiltAndroidCompiler)
        add(hiltCompiler)
    }


    val appLibraries = arrayListOf<String>().apply {
        add(appcompat)
        add(materialUI)
        add(constraintLayout)
        add(androidXlegacy)
        add(androidXcore)
        add(navFrag)
        add(navUI)
        add(coroutinesCore)
        add(coroutinesAndroid)
        add(hiltAndroid)
        add(hiltViewModel)
        add(viewmodelLifecycle)
        add(livedataLifecycle)
        add(extensionLifecycle)
        add(fragmentLifecycle)
//        add(javaxAnnotation)

    }


    private const val junit = "junit:junit:4.13.2"
    private const val extJUnit = "androidx.test.ext:junit:1.1.2"
    private const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }

    val apiLibraries = arrayListOf<String>().apply {
//        add(javalite)
//        add(stublite)

    }

    val runtimeLibraries = arrayListOf<String>().apply {
        add(grpcOkhttp)
    }


}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}


fun DependencyHandler.api(list: List<String>) {
    list.forEach { dependency ->
        add("api", dependency)
    }
}


fun DependencyHandler.runtimeOnly(list: List<String>) {
    list.forEach { dependency ->
        add("runtimeOnly", dependency)
    }
}

fun DependencyHandler.compileOnly(list: List<String>) {
    list.forEach { dependency ->
        add("compileOnly", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}