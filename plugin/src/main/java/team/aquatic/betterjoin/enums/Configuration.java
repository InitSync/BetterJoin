package team.aquatic.betterjoin.enums;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.modules.files.FileActionType;
import team.aquatic.betterjoin.managers.ConfigurationManager;

import java.util.List;
import java.util.Objects;

public class Configuration {
	private final ConfigurationManager configurationManager;
	
	public Configuration(@NotNull BetterJoin plugin) {
		this.configurationManager = Objects.requireNonNull(plugin, "BetterJoin instance is null")
			 .configurationManager();
	}
	
	public void doSomething(@NotNull FileActionType fileActionType) {
		Objects.requireNonNull(fileActionType, "The file action type is null.");
		
		switch (fileActionType) {
			case RELOAD -> this.configurationManager.reload("config.yml");
			case SAVE -> this.configurationManager.save("config.yml");
		}
	}
	
	/**
	 * It returns a string from the file
	 *
	 * @param path The path to the value you want to get.
	 * @return A string.
	 */
	public @NotNull String string(@NotNull String path) {
		Validate.notEmpty(path, "The path requested is empty.");
		
		return this.configurationManager
			 .file("config.yml")
			 .getString(path);
	}
	
	/**
	 * This function returns an integer from the file.
	 *
	 * @param path The path to the value you want to get.
	 * @return an int
	 */
	public int integer(@NotNull String path) {
		Validate.notEmpty(path, "The path requested is empty.");
		
		return this.configurationManager
			 .file("config.yml")
			 .getInt(path);
	}
	
	/**
	 * Get a list of strings from the file and path.
	 *
	 * @param path The path to the value you want to get.
	 * @return A list of strings.
	 */
	public @NotNull List<String> stringList(@NotNull String path) {
		Validate.notEmpty(path, "The path requested is empty.");
		
		return this.configurationManager
			 .file("config.yml")
			 .getStringList(path);
	}
	
	/**
	 * Returns a configuration section from the file and path.
	 *
	 * @param path The path to the section.
	 * @return A ConfigurationSection object.
	 */
	public @Nullable ConfigurationSection section(@NotNull String path) {
		Validate.notEmpty(path, "The path requested is empty.");
		
		return this.configurationManager
			 .file("config.yml")
			 .getConfigurationSection(path);
	}
	
	/**
	 * It checks if a path in the file is a boolean
	 *
	 * @param path The path to the value you want to check.
	 * @return A boolean value.
	 */
	public boolean check(@NotNull String path) {
		Validate.notEmpty(path, "The path requested is empty.");
		
		return this.configurationManager
			 .file("config.yml")
			 .getBoolean(path);
	}
}
