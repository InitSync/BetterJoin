package team.aquatic.betterjoin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.api.UserServerJoinEvent;
import team.aquatic.betterjoin.api.UserServerQuitEvent;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.enums.modules.files.FileType;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;

public class PlayerQuitListener implements Listener {
	private final BetterJoin plugin;
	private final Configuration configuration;
	
	private String message;
	private String group;
	
	public PlayerQuitListener(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		
		this.group = this.plugin
			 .luckPerms()
			 .getUserManager()
			 .getUser(player.getUniqueId())
			 .getPrimaryGroup();
		
		final UserServerQuitEvent serverQuitEvent = new UserServerQuitEvent();
		this.plugin
			 .getServer()
			 .getPluginManager()
			 .callEvent(serverQuitEvent);
		if (!serverQuitEvent.isCancelled()) {
			if (this.configuration.section(FileType.CONFIG, "config.server.groups." + group) != null) {
				this.message = this.configuration.string(
					 FileType.CONFIG,
					 "config.server.groups." + group + ".join"
				);
			} else {
				this.message = null;
				event.setQuitMessage("");
				return;
			}
			
			event.setQuitMessage(Utils.parse(player, message));
		}
	}
}
