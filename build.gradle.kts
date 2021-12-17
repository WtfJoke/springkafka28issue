import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


object DependencyVersions {
	const val Confluent = "7.0.1"
	const val Avro = "1.11.0"
}

plugins {
	id("org.springframework.boot") version "2.5.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.github.davidmc24.gradle.plugin.avro") version "1.3.0"
	kotlin("jvm") version "1.6.0"
	kotlin("plugin.spring") version "1.6.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven(url = uri("https://packages.confluent.io/maven/"))
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.apache.avro:avro:${DependencyVersions.Avro}")
	implementation("io.confluent:kafka-avro-serializer:${DependencyVersions.Confluent}")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.generateAvroJava {
	source = fileTree("src/main/resources/avro")
}

tasks.generateAvroProtocol {
	source = fileTree("src/main/resources/avro")
}