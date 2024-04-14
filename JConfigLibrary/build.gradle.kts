plugins {
    id("java-library")
    kotlin("jvm") version "1.9.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    //Configuration
    implementation("com.typesafe:config:1.4.3")
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    implementation("org.yaml:snakeyaml:2.2")
}