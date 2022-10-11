plugins {
    kotlin("multiplatform") version "1.7.20"
    `maven-publish`
    id("org.jetbrains.dokka") version "1.5.0"
}

allprojects {
    group = "com.github.nanoflakes"
    version = "1.2.2"

    repositories {
        mavenCentral()
    }
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
