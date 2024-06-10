import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
}

repositories {
    mavenCentral()
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser {
//            commonWebpackConfig {
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(project.projectDir.path)
//                    }
//                }
//            }
//        }
//    }
    
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    sourceSets {
        commonMain.dependencies {
//            implementation(kotlin("stdlib-common"))

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Ktor Client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.websockets)
            implementation(libs.ktor.client.mock)

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            // Crypto
            implementation(libs.secp256k1.kmp)

            // Serialization
            implementation(libs.kotlinx.serialization.json)

            // Data&Time
            implementation(libs.kotlinx.datetime)
        }

        jvmMain.dependencies {
//            implementation(kotlin("stdlib"))
            implementation(libs.secp256k1.kmp.jni.jvm)
        }

        androidMain.dependencies {
//            implementation(kotlin("stdlib"))
            implementation(libs.secp256k1.kmp.jni.android)
        }
    }
}

android {
    namespace = "com.appollo41.loop.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
