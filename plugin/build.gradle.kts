plugins {
	id("java")
	id("com.github.johnrengelman.shadow") version("7.1.2")
	id("net.minecrell.plugin-yml.bukkit") version("0.5.2")
}

val pluginGroup = property("group") as String
val pluginLibsDirectory = property("libsDirectory") as String
val pluginVersion = property("version") as String
val pluginApiVersion = property("apiVersion") as String
val pluginDescription = "An amazing plugin to customize the entry/left of your server/lobby!"

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
	maven("https://repo.papermc.io/repository/maven-public/")
	maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
	maven("https://jitpack.io/")
	mavenCentral()
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
	compileOnly("me.clip:placeholderapi:2.11.2")
	compileOnly("net.luckperms:api:5.4")
	
	implementation(project(":api"))
	implementation("org.jetbrains:annotations:23.0.0")
	implementation("commons-lang:commons-lang:2.6")
}

bukkit {
	name = "BetterJoin"
	main = "$pluginGroup.BetterJoin"
	authors = listOf("InitSync")
	
	version = pluginVersion
	apiVersion = pluginApiVersion
	
	depend = listOf("LuckPerms")
	softDepend = listOf("PlaceholderAPI")
	
	description = pluginDescription
	
	permissions {
		register("betterjoin.*") {
			children = listOf(
				 "betterjoin.help",
				 "betterjoin.reload",
			)
		}
		register("betterjoin.help")
		register("betterjoin.reload")
	}
	
	commands {
		register("betterjoin") {
			description = "-> Command to handle the plugin."
			aliases = listOf("bj", "bjoin")
		}
	}
}

tasks {
	shadowJar {
		archiveFileName.set("[AS] BetterJoin-$pluginVersion.jar")
		destinationDirectory.set(file("$rootDir/bin/"))
		minimize()
		
		relocate("org.jetbrains.annotations", "$pluginLibsDirectory.jetbrains")
		relocate("org.apache.commons", "$pluginLibsDirectory.apache")
	}
	
	withType<JavaCompile> {
		options.encoding = "UTF-8"
	}
	
	clean {
		delete("$rootDir/bin/")
	}
}
