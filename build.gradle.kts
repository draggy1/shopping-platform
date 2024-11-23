plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register("dockerComposeUp", Exec::class) {
	group = "docker"
	description = "Start the application using Docker Compose"
	commandLine("docker", "compose", "up", "--build", "-d")
}

tasks.register("dockerComposeDown", Exec::class) {
	group = "docker"
	description = "Stop and remove Docker Compose containers"
	commandLine("docker", "compose", "down")
}