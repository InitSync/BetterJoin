package team.aquatic.betterjoin.actions.types;

import com.cryptomorin.xseries.XSound;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.actions.ActionExecutable;
import team.aquatic.betterjoin.enums.modules.actions.ActionType;
import team.aquatic.betterjoin.utils.LogPrinter;

import java.util.Objects;

public class SoundActionType extends ActionExecutable {
	private String[] split;
	
	@Override
	public @NotNull ActionType actionType() {
		return ActionType.SOUND;
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

		int volume;
		int pitch;
		try {
			volume = Integer.parseInt(this.split[1]);
			pitch = Integer.parseInt(this.split[2]);
		} catch (NumberFormatException exception) {
			LogPrinter.error("Failed to parse the sound action parameters.");
			
			exception.printStackTrace();
			return;
		}
		
		player.playSound(
			 player.getLocation(),
			 XSound.matchXSound(this.split[0]).get().parseSound(),
			 volume, pitch
		);
	}
}
