package team.aquatic.betterjoin.enums.modules.config;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.enums.Path;

public enum MessagesPathImpl implements Path {
	NO_PERM ("no-permission");
	
	private final String path;
	
	MessagesPathImpl(@NotNull String path) {
		Validate.notEmpty(path, "The messages path is empty.");
		
		this.path = path;
	}
	
	@Override
	public @NotNull String getPath() {
		return "messages." + this.path;
	}
}