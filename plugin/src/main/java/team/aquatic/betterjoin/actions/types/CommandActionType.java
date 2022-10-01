package team.aquatic.betterjoin.actions.types;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.actions.ActionExecutable;
import team.aquatic.betterjoin.enums.modules.actions.ActionType;

import java.util.Objects;

public class CommandActionType extends ActionExecutable {
	private String[] split;
	
	@Override
	public @NotNull ActionType actionType() {
		return ActionType.COMMAND;
	}
	
	@Override
	public void executeAction(
		 @NotNull BetterJoin plugin,
		 @NotNull Player player,
		 @NotNull String container
	) {
		Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		Objects.requireNonNull(player, "The player is null.");
		Validate.notEmpty(container, "The container is empty.");
		
		this.split = container.split(";");
		
		final String command = this.split[0];
		
		if (Boolean.parseBoolean(this.split[1])) {
			plugin.getServer()
				 .dispatchCommand(
						Bukkit.getConsoleSender(),
						command
				 );
			return;
		}
		
		plugin.getServer().dispatchCommand(player, command);
	}
}
