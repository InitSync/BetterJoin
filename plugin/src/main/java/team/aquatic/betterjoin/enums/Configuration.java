package team.aquatic.betterjoin.enums;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.enums.modules.files.FileActionType;
import team.aquatic.betterjoin.enums.modules.files.FileType;
import team.aquatic.betterjoin.managers.ConfigurationManager;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Configuration {
	private final ConfigurationManager configurationManager;
	
	public Configuration(@NotNull ConfigurationManager configurationManager) {
		this.configurationManager = Objects.requireNonNull(configurationManager);
	}
	
	public void doSomething(@NotNull FileType fileType, @NotNull FileActionType fileActionType) {
		Objects.requireNonNull(fileType, "The file type is null.");
		Objects.requireNonNull(fileActionType, "The file action type is null.");
		
		switch (fileType) {
			case CONFIG:
				switch (fileActionType) {
					case RELOAD:
						this.configurationManager.reload("config.yml");
						break;
					case SAVE:
						this.configurationManager.save("config.yml");
						break;
				}
				break;
			case MESSAGES:
				switch (fileActionType) {
					case RELOAD:
						this.configurationManager.reload("messages.yml");
						break;
					case SAVE:
						this.configurationManager.save("messages.yml");
						break;
				}
				break;
		}
	}
	
	/**
	 * It returns a string from a file
	 *
	 * @param fileType The type of file you want to get the string from.
	 * @param path The path to the value you want to get.
	 * @return A string.
	 */
	public @NotNull String string(@NotNull FileType fileType, @NotNull String path) {
		Objects.requireNonNull(fileType, "The file type is null.");
		Validate.notEmpty(path, "The path requested is empty.");
		
		switch (fileType) {
			case CONFIG:
				return this.configurationManager
					 .file("config.yml")
					 .getString(path);
			case MESSAGES:
				return this.configurationManager
					 .file("messages.yml")
					 .getString(path);
		}
		return "";
	}
	
	/**
	 * This function returns an integer from a file.
	 *
	 * @param fileType The file type you want to get the integer from.
	 * @param path The path to the value you want to get.
	 * @return -10234
	 */
	public int integer(@NotNull FileType fileType, @NotNull String path) {
		Objects.requireNonNull(fileType, "The file type is null.");
		Validate.notEmpty(path, "The path requested is empty.");
		
		switch (fileType) {
			case CONFIG:
				return this.configurationManager
					 .file("config.yml")
					 .getInt(path);
			case MESSAGES:
				return this.configurationManager
					 .file("messages.yml")
					 .getInt(path);
		}
		return -10234;
	}
	
	/**
	 * "Get a list of strings from the specified file type and path."
	 *
	 * The first thing we do is check if the file type is null. If it is, we throw a NullPointerException
	 *
	 * @param fileType The file type you want to get the string list from.
	 * @param path The path to the value you want to get.
	 * @return A list of strings.
	 */
	public @NotNull List<String> stringList(@NotNull FileType fileType, @NotNull String path) {
		Objects.requireNonNull(fileType, "The file type is null.");
		Validate.notEmpty(path, "The path requested is empty.");
		
		switch (fileType) {
			case CONFIG:
				return this.configurationManager
					 .file("config.yml")
					 .getStringList(path);
			case MESSAGES:
				return this.configurationManager
					 .file("messages.yml")
					 .getStringList(path);
		}
		return Collections.emptyList();
	}
	
	/**
	 * It checks if a path in a file is a boolean
	 *
	 * @param fileType The file type you want to check.
	 * @param path The path to the value you want to check.
	 * @return A boolean value.
	 */
	public boolean check(@NotNull FileType fileType, @NotNull String path) {
		Objects.requireNonNull(fileType, "The file type is null.");
		Validate.notEmpty(path, "The path requested is empty.");
		
		switch (fileType) {
			case CONFIG:
				return this.configurationManager
					 .file("config.yml")
					 .getBoolean(path);
			case MESSAGES:
				return this.configurationManager
					 .file("messages.yml")
					 .getBoolean(path);
		}
		return false;
	}
}
