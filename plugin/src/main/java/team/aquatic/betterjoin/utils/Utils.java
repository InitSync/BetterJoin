package team.aquatic.betterjoin.utils;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Utils {
	public static boolean papiIsAvailable;
	
	/**
	 * It replaces all the variables in the text with the player's information
	 *
	 * @param player The player to parse the text for.
	 * @param text The text to parse.
	 * @return The text with the variables replaced.
	 */
	public static String parse(@NotNull Player player, @NotNull String text) {
		Objects.requireNonNull(player, "Player is null.");
		
		if (papiIsAvailable) text = PlaceholderAPI.setPlaceholders(player, text);
		
		text = text.replace("<player_name>", player.getName())
			 .replace("<player_level>", Integer.toString(player.getLevel()))
			 .replace("<player_exp>", Integer.toString(player.getTotalExperience()))
			 .replace("<player_world>", player.getWorld().getName())
			 .replace("<player_kills>", Integer.toString(player.getStatistic(Statistic.PLAYER_KILLS)))
			 .replace("<player_server>", player.getServer().getName());
		
		return IridiumColorAPI.process(text);
	}
	
	/**
	 * It sends a title and subtitle to a player
	 *
	 * @param player The player to send the title to.
	 * @param title The title to send.
	 * @param subtitle The subtitle to send.
	 * @param fadeIn The time it takes for the title to fade in.
	 * @param stay The amount of time in ticks the title should stay on the screen.
	 * @param fadeOut The time it takes for the title to fade out.
	 */
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
	
	/**
	 * `Send an action bar message to a player.`
	 *
	 * @param plugin The JavaPlugin instance.
	 * @param player The player to send the action bar to.
	 * @param message The message to send.
	 * @param duration The duration in ticks that the action bar will be displayed for.
	 */
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
