import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.16"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	kotlin("kapt") version "1.9.20"
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

noArg {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

group = "team.themoment.gsmNetworking"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
	mavenCentral()
}

dependencies {
	/* spring app */
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	/* thymeleaf */
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

	/** validation **/
	implementation("org.springframework.boot:spring-boot-starter-validation")

	/** jpa **/
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	/** queryDsl **/
	implementation("com.querydsl:querydsl-jpa:5.0.0")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

	/* DB */
	implementation ("com.fasterxml.uuid:java-uuid-generator:4.1.1")
	implementation("mysql:mysql-connector-java:8.0.32")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	/* test */
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")
	implementation("io.kotest:kotest-extensions-spring:4.4.3")
	testImplementation("io.mockk:mockk:1.12.0")

	/* aws */
	implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:3.0.1")

	/* emali */
	implementation("org.springframework.boot:spring-boot-starter-mail:2.6.7")

	/* security */
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springframework.security:spring-security-oauth2-client")

	/* jwt */
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	/* websocket */
	implementation("org.springframework.boot:spring-boot-starter-websocket")

	/* actuator */
	implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
