import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.serialization") version "1.6.0"

	id("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "horizonsend"
version = "1.0"

repositories {
	mavenCentral()

	maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
	maven { url = uri("https://repo.essentialsx.net/releases/") }
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
	implementation("net.dv8tion:JDA:5.0.0-alpha.2")

	compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")

	compileOnly("net.essentialsx:EssentialsX:2.19.0")
	compileOnly("net.luckperms:api:5.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "16"
}

tasks.shadowJar {
	minimize()
}