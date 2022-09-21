package team.aquatic.betterjoin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.api.UserServerQuitEvent;
import team.aquatic.betterjoin.managers.GroupManager;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;
import java.util.UUID;

public class PlayerQuitListener implements Listener {
	private final BetterJoin plugin;
	private final GroupManager groupManager;
	
	public PlayerQuitListener(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.groupManager = this.plugin.groupManager();
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		
		final UserServerQuitEvent serverQuitEvent = new UserServerQuitEvent();
		this.plugin
			 .getServer()
			 .getPluginManager()
			 .callEvent(serverQuitEvent);
		if (!serverQuitEvent.isCancelled()) {
			serverQuitEvent.setQuitMessage(this.groupManager.groupQuitMessage(uuid));
			event.setQuitMessage(Utils.parse(player, serverQuitEvent.getQuitMessage()));
		}
	}
}
