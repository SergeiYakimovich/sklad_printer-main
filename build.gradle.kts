val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val ktor_openapi_generator_version: String by project
val ebean_version: String by project
val jdbc_postgresql_version: String by project
val ebean_migration_version: String by project
val ebean_migration_auto_version: String by project
val mapstruct_version: String by project

plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("kapt") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
    id("io.ktor.plugin") version "2.2.2"
    id("io.ebean") version "13.6.5"
}

group = "site.shaerware"
version = "0.0.1"

application {
    mainClass.set("site.shaerware.store.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-openapi:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-resources:$ktor_version")
    implementation("io.ktor:ktor-server-auto-head-response:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")
    implementation("io.ebean:ebean:$ebean_version")
    implementation("io.ebean:ebean-migration:$ebean_migration_version")
    implementation("io.ebean:ebean-migration-auto:$ebean_migration_auto_version")
    implementation("org.postgresql:postgresql:$jdbc_postgresql_version")
    implementation("io.ktor:ktor-server-cors:2.2.3")
    kapt("io.ebean:kotlin-querybean-generator:$ebean_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.ebean:ebean-test:$ebean_version")
}

tasks.named<JavaExec>("run") {
    systemProperty("project_version", project.version ?: "")
}

