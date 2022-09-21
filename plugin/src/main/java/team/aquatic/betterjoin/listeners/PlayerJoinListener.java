package team.aquatic.betterjoin.listeners;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Particle;
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
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;
import java.util.UUID;

public class PlayerJoinListener implements Listener {
	private final BetterJoin plugin;
	private final Configuration configuration;
	private final GroupManager groupManager;

	private String group;
	
	public PlayerJoinListener(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
		this.groupManager = this.plugin.groupManager();
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final UUID uuid = player.getUniqueId();
		
		this.group = this.groupManager.getPlayerGroup(uuid);
		
		this.sendMotd(player);
		
		final UserServerJoinEvent serverJoinEvent = new UserServerJoinEvent();
		this.plugin
			 .getServer()
			 .getPluginManager()
			 .callEvent(serverJoinEvent);
		if (!serverJoinEvent.isCancelled()) {
			serverJoinEvent.setJoinMessage(this.groupManager.groupJoinMessage(uuid));
			event.setJoinMessage(Utils.parse(player, serverJoinEvent.getJoinMessage()));
			
			if (this.configuration.check(
				 FileType.CONFIG,
				 "config.server.groups." + group + ".allow-particles")
			) {
				XParticle.circle(5, 1, ParticleDisplay.display(player.getLocation(), Particle.ENCHANTMENT_TABLE));
			}
			
			this.groupManager.executeGroupActions(player);
		}
	}
	
	private void sendMotd(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		this.configuration
			 .stringList(FileType.CONFIG, "config.server.motd")
			 .forEach(string -> player.sendMessage(IridiumColorAPI.process(string)));
	}
}
