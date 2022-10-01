package team.aquatic.betterjoin.actions.types;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.actions.ActionExecutable;
import team.aquatic.betterjoin.enums.modules.actions.ActionType;
import team.aquatic.betterjoin.utils.LogPrinter;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;

public class TitleActionType extends ActionExecutable {
	private String[] split;
	
	@Override
	public @NotNull ActionType actionType() {
		return ActionType.TITLE;
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
		
		int fadeIn;
		int stay;
		int fadeOut;
		try {
			fadeIn = Integer.parseInt(this.split[2]);
			stay = Integer.parseInt(this.split[3]);
			fadeOut = Integer.parseInt(this.split[4]);
		} catch (NumberFormatException exception) {
			LogPrinter.error("Failed to parse the title action parameters.");
			
			exception.printStackTrace();
			return;
		}
		
		Utils.showTitle(
			 player,
			 this.split[0], this.split[1],
			 fadeIn, stay, fadeOut
		);
	}
}
