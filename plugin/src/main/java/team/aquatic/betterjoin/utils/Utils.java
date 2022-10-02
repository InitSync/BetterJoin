package team.aquatic.betterjoin.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.title.Title;
import org.apache.commons.lang.Validate;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
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
	public static @NotNull Component parse(@NotNull Player player, @NotNull String text) {
		Objects.requireNonNull(player, "Player is null.");
		
		if (papiIsAvailable) text = PlaceholderAPI.setPlaceholders(player, text);
		
		return MiniMessage.miniMessage().deserialize(text,
			 Placeholder.parsed("player_name", player.getName()),
			 Placeholder.parsed("player_level", Integer.toString(player.getLevel())),
			 Placeholder.parsed("player_exp", Integer.toString(player.getTotalExperience())),
			 Placeholder.parsed("player_world", player.getWorld().getName()),
			 Placeholder.parsed("player_kills", Integer.toString(player.getStatistic(Statistic.PLAYER_KILLS))),
			 Placeholder.parsed("player_server", player.getServer().getName())
		);
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
	public static void showTitle(
		 @NotNull Player player,
		 @NotNull String title, @NotNull String subtitle,
		 int fadeIn, int stay, int fadeOut
	) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(title, "The title is empty.");
		Validate.notEmpty(subtitle, "The subtitle is empty.");
		
		player.showTitle(Title.title(
			 parse(player, title),
			 parse(player, subtitle),
			 Title.Times.times(
					Duration.ofSeconds(fadeIn),
				  Duration.ofSeconds(stay),
				  Duration.ofSeconds(fadeOut)
			 )
		));
	}
	
	/**
	 * `Send an action bar message to a player.`
	 *
	 * @param plugin The JavaPlugin instance.
	 * @param player The player to send the action bar to.
	 * @param message The message to send.
	 * @param duration The duration in ticks that the action bar will be displayed for.
	 */
	public static void showTempActionBar(
		 @NotNull JavaPlugin plugin,
		 @NotNull Player player,
		 @NotNull String message,
		 long duration
	) {
		Objects.requireNonNull(plugin, "JavaPlugin instance is null.");
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(message, "The message to send is empty.");
		
		if (duration < 1) return;
		
		new BukkitRunnable() {
			long repeater = duration;
			
			@Override
			public void run() {
				player.sendActionBar(parse(player, message));
				repeater -= 40L;
				if (repeater - 40L < -20L) cancel();
			}
		}.runTaskTimerAsynchronously(plugin, 0L, 40L);
	}
	
	public static void showActionBar(@NotNull Player player, @NotNull String message) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(message, "The message is empty.");
		
		player.sendActionBar(parse(player, message));
	}
}
