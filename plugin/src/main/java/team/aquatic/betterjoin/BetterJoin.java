package team.aquatic.betterjoin;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.interfaces.ConfigHandler;
import team.aquatic.betterjoin.managers.ConfigurationManager;
import team.aquatic.betterjoin.utils.LogPrinter;

public final class BetterJoin extends JavaPlugin {
	private static BetterJoin instance;
	
	private final PluginDescriptionFile descriptionFile = this.getDescription();
	
	public final String author = String.join("", this.descriptionFile.getAuthors());
	public final String version = this.descriptionFile.getVersion();
	
	private ConfigurationManager configurationManager;
	private Configuration configuration;
	
	public static @NotNull BetterJoin instance() {
		if (instance == null) {
			throw new IllegalStateException("Cannot access to BetterJoin instance because is disabled!");
		}
		return instance;
	}
	
	public @NotNull ConfigurationManager configurationManager() {
		if (this.configurationManager == null) {
			throw new IllegalStateException("Failed to get the ConfigurationManager instance because is"
				 + " null.");
		}
		return this.configurationManager;
	}
	
	public @NotNull Configuration configuration() {
		if (this.configuration == null) {
			throw new IllegalStateException("Failed to get the Configuration instance because is"
				 + " null.");
		}
		return this.configuration;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		this.configurationManager = ConfigHandler.newInstance(this, "config.yml");
		this.configuration = ConfigHandler.newInstance(this.configurationManager);
		
		LogPrinter.info(
			 "Started plugin successfully.",
			 "Developed by " + this.author + " | v" + this.version
		);
	}
	
	@Override
	public void onDisable() {
		LogPrinter.info(
			 "Disabling plugin...",
			 "Developed by " + this.author + " | v" + this.version
		);
		
		if (this.configurationManager != null && this.configuration != null) {
			this.configuration = null;
			this.configurationManager = null;
		}
		if (instance != null) instance = null;
	}
}
