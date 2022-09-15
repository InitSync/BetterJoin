package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.managers.ConfigurationManager;

public interface ConfigHandler {
	static Configuration newInstance(@NotNull ConfigurationManager manager) {
		return new Configuration(manager);
	}
	
	static ConfigurationManager newInstance(@NotNull BetterJoin plugin, @NotNull String... files) {
		return new ConfigurationManager(plugin, files);
	}
}
