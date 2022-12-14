package team.aquatic.betterjoin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.commands.MainCommand;
import team.aquatic.betterjoin.commands.MainCommandTabCompleter;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.interfaces.*;
import team.aquatic.betterjoin.listeners.PlayerJoinListener;
import team.aquatic.betterjoin.listeners.PlayerQuitListener;
import team.aquatic.betterjoin.managers.ActionManager;
import team.aquatic.betterjoin.managers.ConfigurationManager;
import team.aquatic.betterjoin.managers.GroupManager;
import team.aquatic.betterjoin.utils.LogPrinter;
import team.aquatic.betterjoin.utils.Utils;

public final class BetterJoin extends JavaPlugin {
	private static BetterJoin instance;
	
	private final PluginDescriptionFile descriptionFile = this.getDescription();
	private final PluginManager pluginManager = this.getServer().getPluginManager();
	
	public final String author = String.join("", this.descriptionFile.getAuthors());
	public final String version = this.descriptionFile.getVersion();
	
	private LuckPerms luckPerms;
	private ConfigurationManager configurationManager;
	private Configuration configuration;
	private GroupManager groupManager;
	private ActionManager actionManager;
	
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
	 * If LuckPerms is null, throw an exception. Otherwise, return LuckPerms.
	 *
	 * @return LuckPerms instance
	 */
	public @NotNull LuckPerms luckPerms() {
		if (this.luckPerms == null) {
			throw new IllegalStateException("Cannot access to LuckPerms instance.");
		}
		return this.luckPerms;
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
	
	/**
	 * If the GroupManager instance is null, throw an exception. Otherwise, return the GroupManager
	 * instance.
	 *
	 * @return The GroupManager instance.
	 */
	public @NotNull GroupManager groupManager() {
		if (this.groupManager == null) {
			throw new IllegalStateException("Failed to get the GroupManager instance because is"
				 + " null.");
		}
		return this.groupManager;
	}
	
	/**
	 * If the actionManager is null, throw an exception. Otherwise, return the actionManager
	 *
	 * @return The ActionManager instance.
	 */
	public @NotNull ActionManager actionManager() {
		if (this.actionManager == null) {
			throw new IllegalStateException("Failed to get the ActionManager instance because the "
				 + "plugin instance is null.");
		}
		return this.actionManager;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		this.luckPerms = LuckPermsProvider.get();
		this.loadConfiguration();
		this.groupManager = GroupInterface.newManagerInstance(this);
		this.actionManager = ActionInterface.newManagerInstance(this);
		this.dependency();
		this.loaders();
		
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
		if (this.groupManager != null) this.groupManager = null;
		if (this.actionManager != null) {
			this.actionManager.unregisterAll();
			this.actionManager = null;
		}
		if (instance != null) instance = null;
	}
	
	private void loadConfiguration() {
		this.configurationManager = ConfigInterface.newManagerInstance(this, "config.yml");
		this.configuration = ConfigInterface.newConfigurationInstance(this);
	}
	
	private void dependency() {
		if (this.pluginManager
		    .getPlugin("PlaceholderAPI") != null && this.pluginManager
		    .isPluginEnabled("PlaceholderAPI")
		) {
			Utils.papiIsAvailable = true;
		}
	}
	
	private void loaders() {
		LoadersInterface.newCommand(this)
			 .name("betterjoin")
			 .executor(new MainCommand(this))
			 .completer(new MainCommandTabCompleter())
			 .register();
		LoadersInterface.newListener(this)
			 .event(new PlayerJoinListener(this))
			 .register()
			 .event(new PlayerQuitListener(this))
			 .register();
	}
}
