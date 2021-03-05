object Apps {
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val gradle = "3.5.0"
    const val kotlin = "1.3.50"
    const val appcompat = "1.0.2"

    /* test */
    const val junit = "4.12"

    const val navVersion = "2.3.3"
    const val hiltVersion = "2.28.1-alpha"
    const val hiltLifecycle = "1.0.0-alpha02"
    const val hiltClassPathVersion = "2.28-alpha"

}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val navFrag= "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val navUI = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltLifecycle = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLifecycle}"

    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltLifecycle}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
}