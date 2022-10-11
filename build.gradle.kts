plugins {
    kotlin("multiplatform") version "1.5.31"
    `maven-publish`
    id("org.jetbrains.dokka") version "1.5.0"
}

group = "com.github.nanoflakes"
version = "1.2.2"

repositories {
    mavenCentral()
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

    linuxArm64()
    linuxArm32Hfp()
    linuxX64()

    mingwX64()
    mingwX86()

    sourceSets {
        val commonMain by getting

        val jvmMain by getting
        val jsMain by getting

        val linuxArm64Main by getting
        val linuxArm32HfpMain by getting
        val linuxX64Main by getting

        val mingwX64Main by getting
        val mingwX86Main by getting

        val nativeMain by creating {
            dependsOn(commonMain)

            linuxArm64Main.dependsOn(this)
            linuxArm32HfpMain.dependsOn(this)
            linuxX64Main.dependsOn(this)

            mingwX64Main.dependsOn(this)
            mingwX86Main.dependsOn(this)
        }
    }
}

tasks {
    register<Jar>("dokkaJar") {
        from(dokkaHtml)
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
    }
}

publishing {
    publications.withType<MavenPublication> {
        artifact(tasks["dokkaJar"])
    }
    // select the repositories you want to publish to
    repositories {
        maven {
            url = uri("https://maven.cafeteria.dev/releases")

            credentials {
                username = "${project.property("mcdUsername")}"
                password = "${project.property("mcdPassword")}"
            }
            authentication {
                create("basic", BasicAuthentication::class.java)
            }
        }
        mavenLocal()
    }
}
