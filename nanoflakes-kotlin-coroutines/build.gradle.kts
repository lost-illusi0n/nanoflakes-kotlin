plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    js(BOTH) {
        browser()
        nodejs()
    }

    linuxX64()
    mingwX64()

// the following targets are not yet supported by kotlinx-coroutines
//    linuxArm64()
//    linuxArm32Hfp()
//    mingwX86()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation(project(":nanoflakes-kotlin"))
            }
        }
    }
}