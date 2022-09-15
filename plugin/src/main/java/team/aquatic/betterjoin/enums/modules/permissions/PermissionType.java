package team.aquatic.betterjoin.enums.modules.permissions;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

public enum PermissionType {
	HELP_CMD ("help"),
	RELOAD_CMD ("reload");
	
	private final String perm;
	
	PermissionType(@NotNull String perm) {
		Validate.notEmpty(perm, "The permission value is empty.");
		
		this.perm = perm;
	}
	
	public @NotNull String getPerm() {
		return "betterjoin." + this.perm;
	}
}
