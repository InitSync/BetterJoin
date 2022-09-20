package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.managers.ConfigurationManager;

public interface ConfigInterface {
	/**
	 * Create a new instance of the Configuration class, passing the ConfigurationManager instance as a
	 * parameter.
	 *
	 * @param manager The ConfigurationManager instance that this configuration is associated with.
	 * @return A new instance of the Configuration class.
	 */
	static Configuration newConfigurationInstance(@NotNull ConfigurationManager manager) {
		return new Configuration(manager);
	}
	
	/**
	 * Create a new instance of the ConfigurationManager class, passing in the plugin instance and the
	 * list of files to load.
	 *
	 * @param plugin The plugin instance.
	 * @return A new instance of the ConfigurationManager class.
	 */
	static ConfigurationManager newManagerInstance(
		 @NotNull BetterJoin plugin,
		 @NotNull String... files
	) {
		return new ConfigurationManager(plugin, files);
	}
}
