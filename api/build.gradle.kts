plugins {
	id("java")
}

repositories {
	maven("https://repo.papermc.io/repository/maven-public/")
	mavenCentral()
}

java{
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
	
	implementation("org.jetbrains:annotations:23.0.0")
	implementation("commons-lang:commons-lang:2.6")
}