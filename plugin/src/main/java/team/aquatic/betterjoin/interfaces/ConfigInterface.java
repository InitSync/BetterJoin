package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.managers.ConfigurationManager;

public interface ConfigInterface {

	static Configuration newConfigurationInstance(@NotNull BetterJoin plugin) {
		return new Configuration(plugin);
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
