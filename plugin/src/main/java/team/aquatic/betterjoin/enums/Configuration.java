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
	
	public @NotNull String string(@NotNull FileType fileType, @NotNull String path) {
		Objects.requireNonNull(fileType, "The file type is null.");
		Validate.notEmpty(path, "The path requested is empty.");
		
		switch (fileType) {
			case CONFIG:
				return Objects.requireNonNull(this.configurationManager
					 .file("config.yml")
					 .getString(path)
				);
			case MESSAGES:
				return Objects.requireNonNull(this.configurationManager
					 .file("messages.yml")
					 .getString(path)
				);
		}
		return "";
	}
	
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
