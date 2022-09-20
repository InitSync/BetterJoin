package team.aquatic.betterjoin.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigurationModel {
	/**
	 * Loads the file with the given name.
	 *
	 * @param fileName The name of the file to load.
	 */
	void load(@NotNull String fileName);
	
	/**
	 * Reloads the specified file
	 *
	 * @param fileName The name of the file to reload.
	 */
	void reload(@NotNull String fileName);
	
	/**
	 * Saves the file with the given name.
	 *
	 * @param fileName The name of the file to save the data to.
	 */
	void save(@NotNull String fileName);
	
	/**
	 * Returns a FileConfiguration object for the given file name.
	 *
	 * @param fileName The name of the file you want to get the FileConfiguration of.
	 * @return A FileConfiguration object.
	 */
	@Nullable FileConfiguration file(@NotNull String fileName);
}
