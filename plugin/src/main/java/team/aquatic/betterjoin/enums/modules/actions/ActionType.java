package team.aquatic.betterjoin.enums.modules.actions;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

public enum ActionType {
	SOUND ("[sound] "),
	EFFECT ("[effect] "),
	TITLE ("[title] "),
	ACTION_BAR ("[actionbar] "),
	FIREWORK ("[firework] "),
	COMMAND ("[command] "),
	BROADCAST ("[broadcast] "),
	MESSAGE ("[message] "),
	CONSOLE ("[console] ");
	
	private final String identifier;
	
	ActionType(@NotNull String identifier) {
		Validate.notEmpty(identifier, "The action identifier is empty.");
		
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
}
