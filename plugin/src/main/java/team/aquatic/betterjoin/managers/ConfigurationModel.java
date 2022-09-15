package team.aquatic.betterjoin.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public interface ConfigurationModel {
	void load(@NotNull String fileName);
	
	void reload(@NotNull String fileName);
	
	void save(@NotNull String fileName);
	
	@NotNull FileConfiguration file(@NotNull String fileName);
}
