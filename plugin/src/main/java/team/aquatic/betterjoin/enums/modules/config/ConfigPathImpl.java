package team.aquatic.betterjoin.enums.modules.config;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.enums.Path;

public enum ConfigPathImpl implements Path {
	PREFIX ("prefix");
	
	private final String path;
	
	ConfigPathImpl(@NotNull String path) {
		Validate.notEmpty(path, "The configuration path is empty.");
		
		this.path = path;
	}
	
	@Override
	public @NotNull String getPath() {
		return "config." + this.path;
	}
}
