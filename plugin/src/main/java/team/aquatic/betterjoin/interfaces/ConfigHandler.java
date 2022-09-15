package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.managers.ConfigurationManager;

public interface ConfigHandler {
	static Configuration instance(@NotNull ConfigurationManager manager) {
		return new Configuration(manager);
	}
}
