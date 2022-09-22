package team.aquatic.betterjoin.listeners;

import com.cryptomorin.xseries.ReflectionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.api.UserServerJoinEvent;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.enums.modules.files.FileType;
import team.aquatic.betterjoin.managers.GroupManager;
import team.aquatic.betterjoin.utils.StringUtils;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;
import java.util.UUID;

public class PlayerJoinListener implements Listener {
	private final BetterJoin plugin;
	private final Configuration configuration;
	private final GroupManager groupManager;
	
	public PlayerJoinListener(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
		this.groupManager = this.plugin.groupManager();
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		
		this.sendMotd(player);
		
		final UserServerJoinEvent serverJoinEvent = new UserServerJoinEvent();
		this.plugin
			 .getServer()
			 .getPluginManager()
			 .callEvent(serverJoinEvent);
		if (!serverJoinEvent.isCancelled()) {
			serverJoinEvent.setJoinMessage(this.groupManager.groupJoinMessage(uuid));
			event.setJoinMessage(Utils.parse(player, serverJoinEvent.getJoinMessage()));
			
			this.groupManager.executeGroupActions(player);
			if (ReflectionUtils.supports(13) && this.configuration.check(
				 FileType.CONFIG,
				 "config.server.allow-particles")
			) {
				this.groupManager.executeGroupParticles(player);
			}
		}
	}
	
	private void sendMotd(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		if (this.configuration.check(FileType.CONFIG, "config.server.clear-chat")) {
			for (int i = 0 ; i <= 300 ; i++) player.sendMessage("");
		}
		
		this.configuration
			 .stringList(FileType.CONFIG, "config.server.motd")
			 .forEach(string -> {
				 string = StringUtils.centerString(player, string);
				 player.sendMessage(string);
			 });
	}
}
