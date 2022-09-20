package team.aquatic.betterjoin.managers;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.utils.LogPrinter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigurationManager implements ConfigurationModel {
	private final BetterJoin plugin;
	private final Map<String, File> fileMap;
	private final Map<String, FileConfiguration> configurationMap;
	
	public ConfigurationManager(@NotNull BetterJoin plugin, @NotNull String... files) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.fileMap = new HashMap<>();
		this.configurationMap = new HashMap<>();
		
		for (String file : files) this.load(file);
	}
	
	/**
	 * Loads the file with the given name.
	 *
	 * @param fileName The name of the file to load.
	 */
	@Override
	public void load(@NotNull String fileName) {
		if (!this.fileMap.containsKey(fileName) && !this.configurationMap.containsKey(fileName)) {
			final File file = new File(this.plugin.getDataFolder(), fileName);
			if (!file.exists()) this.plugin.saveResource(fileName, false);
			
			final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			
			this.fileMap.put(fileName, file);
			this.configurationMap.put(fileName, configuration);
			return;
		}
		
		YamlConfiguration.loadConfiguration(this.fileMap.get(fileName));
	}
	
	/**
	 * Reloads the specified file
	 *
	 * @param fileName The name of the file to reload.
	 */
	@Override
	public void reload(@NotNull String fileName) {
		if (!this.fileMap.containsKey(fileName) && !this.configurationMap.containsKey(fileName)) {
			try {
				this.configurationMap
					 .get(fileName)
					 .load(this.fileMap.get(fileName));
			} catch (InvalidConfigurationException | IOException exception) {
				LogPrinter.config("Failed to reload the file '" + fileName + "'");
				
				exception.printStackTrace();
			}
			return;
		}
		
		LogPrinter.config("The file to reload doesn't exist: '" + fileName + "'");
	}
	
	/**
	 * Saves the file with the given name.
	 *
	 * @param fileName The name of the file to save the data to.
	 */
	@Override
	public void save(@NotNull String fileName) {
		if (!this.fileMap.containsKey(fileName) && !this.configurationMap.containsKey(fileName)) {
			try {
				this.configurationMap
					 .get(fileName)
					 .save(this.fileMap.get(fileName));
			} catch (IOException exception) {
				LogPrinter.config("Failed to save the file '" + fileName + "'");
				
				exception.printStackTrace();
			}
			return;
		}
		
		LogPrinter.config("The file to save doesn't exist: '" + fileName + "'");
	}
	
	/**
	 * Returns a FileConfiguration object for the given file name.
	 *
	 * @param fileName The name of the file you want to get the FileConfiguration of.
	 * @return A FileConfiguration object.
	 */
	@Override
	public @Nullable FileConfiguration file(@NotNull String fileName) {
		if (!this.fileMap.containsKey(fileName) && !this.configurationMap.containsKey(fileName)) {
			LogPrinter.config("Failed to get the file '" + fileName + "' because not exist.");
			return null;
		}
		return this.configurationMap.get(fileName);
	}
}
