package team.aquatic.betterjoin.actions.types;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.actions.ActionExecutable;
import team.aquatic.betterjoin.enums.modules.actions.ActionType;

import java.util.Objects;

public class ConsoleActionType extends ActionExecutable {
	@Override
	public @NotNull ActionType actionType() {
		return ActionType.BROADCAST;
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
		
		plugin.getServer()
			 .getConsoleSender()
			 .sendMessage(IridiumColorAPI.process(container));
	}
}
