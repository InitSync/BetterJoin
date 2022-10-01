package team.aquatic.betterjoin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;

import java.util.Objects;

public class PlayerQuitListener implements Listener {
	private final BetterJoin plugin;
	
	public PlayerQuitListener(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		
		if (this.plugin
			 .groupManager()
			 .isAllowedGroup(player.getUniqueId())
		) {
			event.quitMessage(this.plugin
				 .groupManager()
				 .groupQuitMessage(player));
		}
	}
}
