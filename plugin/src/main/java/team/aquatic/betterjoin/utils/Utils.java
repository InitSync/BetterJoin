package team.aquatic.betterjoin.utils;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Utils {
	public static String parse(@NotNull Player player, @NotNull String text) {
		Objects.requireNonNull(player, "Player is null.");
		
		text = text.replace("<player_name>", player.getName())
			 .replace("<player_level>", Integer.toString(player.getLevel()))
			 .replace("<player_exp>", Integer.toString(player.getTotalExperience()))
			 .replace("<player_world>", player.getWorld().getName())
			 .replace("<player_kills>", Integer.toString(player.getStatistic(Statistic.PLAYER_KILLS)))
			 .replace("<player_server>", player.getServer().getName())
			 .replace("SOLID", "solid").replace("GRADIENT", "gradient").replace("RAINBOW", "rainbow");
		
		return IridiumColorAPI.process(text);
	}
	
	public static void sendTitle(
		 @NotNull Player player,
		 @NotNull String title,
		 @NotNull String subtitle,
		 int fadeIn,
		 int stay,
		 int fadeOut
	) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(title, "The title is empty.");
		Validate.notEmpty(subtitle, "The subtitle is empty.");
		
		Titles.sendTitle(
			 player,
			 fadeIn,
			 stay,
			 fadeOut,
			 IridiumColorAPI.process(title),
			 IridiumColorAPI.process(subtitle)
		);
	}
	
	public static void sendActionBar(
		 @NotNull JavaPlugin plugin,
		 @NotNull Player player,
		 @NotNull String message,
		 long duration
	) {
		Objects.requireNonNull(plugin, "JavaPlugin instance is null.");
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(message, "The message is empty.");
		
		ActionBar.sendActionBar(
			 plugin,
			 player,
			 message,
			 duration
		);
	}
}
