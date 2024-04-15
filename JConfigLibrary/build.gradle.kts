plugins {
    id("java-library")
    kotlin("jvm") version "1.9.0"
    id("maven-publish")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    //Configuration
    implementation("com.typesafe:config:1.4.3")
    implementation("io.github.cdimascio:dotenv-java:2.3.2")
    implementation("org.yaml:snakeyaml:2.2")
}

afterEvaluate{
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.JoseJordanF"
                artifactId = "JConfig-Library"
                version = "1.0.0"

                from(components["kotlin"])
            }
        }
    }
}
