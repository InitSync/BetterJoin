package team.aquatic.betterjoin;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.commands.MainCommand;
import team.aquatic.betterjoin.commands.MainCommandTabCompleter;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.interfaces.ConfigInterface;
import team.aquatic.betterjoin.interfaces.ExpansionInterface;
import team.aquatic.betterjoin.interfaces.LoadersInterface;
import team.aquatic.betterjoin.listeners.PlayerJoinListener;
import team.aquatic.betterjoin.managers.ConfigurationManager;
import team.aquatic.betterjoin.utils.LogPrinter;

public final class BetterJoin extends JavaPlugin {
	private static BetterJoin instance;
	
	private final PluginDescriptionFile descriptionFile = this.getDescription();
	private final PluginManager pluginManager = this.getServer().getPluginManager();
	
	public final String author = String.join("", this.descriptionFile.getAuthors());
	public final String version = this.descriptionFile.getVersion();
	
	private ConfigurationManager configurationManager;
	private Configuration configuration;
	
	/**
	 * If the instance is null, throw an exception
	 *
	 * @return The instance of the BetterJoin class.
	 */
	public static @NotNull BetterJoin instance() {
		if (instance == null) {
			throw new IllegalStateException("Cannot access to BetterJoin instance because is disabled!");
		}
		return instance;
	}
	
	/**
	 * If the configurationManager is null, throw an exception, otherwise return the configurationManager.
	 *
	 * @return The ConfigurationManager instance.
	 */
	public @NotNull ConfigurationManager configurationManager() {
		if (this.configurationManager == null) {
			throw new IllegalStateException("Failed to get the ConfigurationManager instance because is"
				 + " null.");
		}
		return this.configurationManager;
	}
	
	/**
	 * If the configuration is null, throw an exception. Otherwise, return the configuration.
	 *
	 * @return The Configuration instance.
	 */
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
		
		this.configurationManager = ConfigInterface.newManagerInstance(this,
			 "config.yml",
			 "messages.yml"
		);
		this.configuration = ConfigInterface.newConfigurationInstance(this.configurationManager);
		
		if (this.pluginManager
			 .getPlugin("PlaceholderAPI") != null && this.pluginManager
			 .isPluginEnabled("PlaceholderAPI")
		) {
			ExpansionInterface.newExpansionInstance().register();
			
			LogPrinter.info("Registered PlaceholderAPI expansion successfully.");
		}
		
		LoadersInterface.newCommand(this)
			 .name("betterjoin")
			 .executor(new MainCommand(this))
			 .completer(new MainCommandTabCompleter())
			 .register();
		LoadersInterface.newListener(this)
			 .event(new PlayerJoinListener(this))
			 .register();
		
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
