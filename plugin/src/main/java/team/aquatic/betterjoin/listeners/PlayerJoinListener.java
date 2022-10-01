package team.aquatic.betterjoin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.managers.GroupManager;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;

public class PlayerJoinListener implements Listener {
	private final Configuration configuration;
	private final GroupManager groupManager;
	
	public PlayerJoinListener(@NotNull BetterJoin plugin) {
		this.configuration = Objects.requireNonNull(plugin, "BetterJoin instance is null.").configuration();
		this.groupManager = Objects.requireNonNull(plugin, "BetterJoin instance is null.").groupManager();
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		this.sendMotd(player);
		
		if (this.groupManager.isAllowedGroup(player.getUniqueId())) {
			this.groupManager.executeGroupActions(player);
			event.joinMessage(this.groupManager.groupJoinMessage(player));
		}
	}
	
	private void sendMotd(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		if (this.configuration.check("config.server.clear-chat")) {
			for (int i = 0 ; i <= 300 ; i++) player.sendMessage("");
		}
		
		this.configuration
			 .stringList("config.server.motd")
			 .forEach(string -> player.sendMessage(Utils.parse(player, string)));
	}
}
