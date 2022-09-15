package team.aquatic.betterjoin.handlers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.aquatic.betterjoin.BetterJoin;

public class ExpansionHandlerModel extends PlaceholderExpansion {
	@Override
	public boolean canRegister() {
		return true;
	}
	@Override
	public @NotNull String getIdentifier() {
		return "betterjoin";
	}
	
	@Override
	public @NotNull String getAuthor() {
		return "InitSync";
	}
	
	@Override
	public @NotNull String getVersion() {
		return BetterJoin.instance().version;
	}
	
	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
		if (params.contains("player_name")) return player.getName();
		if (params.contains("player_level")) return Integer.toString(player.getLevel());
		if (params.contains("player_exp")) return Integer.toString(player.getTotalExperience());
		if (params.contains("player_world")) return player.getWorld().getName();
		if (params.contains("player_kills")) {
			return Integer.toString(player.getStatistic(
				 Statistic.PLAYER_KILLS
			));
		}
		if (params.contains("player_server")) return player.getServer().getName();
		
		return null;
	}
}
