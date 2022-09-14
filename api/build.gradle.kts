plugins {
	id("java")
}

repositories {
	mavenCentral()
	maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
	compileOnly("com.destroystokyo.paper:paper-api:1.13.2-R0.1-SNAPSHOT")
	
	implementation("org.jetbrains:annotations:23.0.0")
}