package team.aquatic.betterjoin.actions;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.modules.actions.ActionType;

public abstract class ActionExecutable {
	public abstract @NotNull ActionType actionType();
	
	public abstract void executeAction(
		 @NotNull BetterJoin plugin,
		 @NotNull Player player,
		 @NotNull String container
	);
}
