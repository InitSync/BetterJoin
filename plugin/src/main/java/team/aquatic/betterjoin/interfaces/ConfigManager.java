package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.managers.ConfigurationManager;

public interface ConfigManager {
	static ConfigurationManager instance(@NotNull BetterJoin plugin, @NotNull String... files) {
		return new ConfigurationManager(plugin, files);
	}
}
