package team.aquatic.betterjoin.listeners;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.api.ParticleShowEvent;
import team.aquatic.betterjoin.api.UserServerJoinEvent;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.enums.modules.files.FileType;
import team.aquatic.betterjoin.managers.GroupManager;
import team.aquatic.betterjoin.managers.ParticleManager;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;
import java.util.UUID;

public class PlayerJoinListener implements Listener {
	private final BetterJoin plugin;
	private final Configuration configuration;
	private final GroupManager groupManager;
	private final ParticleManager particleManager;

	private String group;
	
	public PlayerJoinListener(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
		this.groupManager = this.plugin.groupManager();
		this.particleManager = this.plugin.particleManager();
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
			
			this.groupManager.executeGroupActions(player);
			
			this.executeGroupParticles(player);
		}
	}
	
	private void sendMotd(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		if (this.configuration.check(FileType.CONFIG, "config.server.clear-chat")) {
			for (int i = 0 ; i <= 300 ; i++) player.sendMessage("");
		}
		
		this.configuration
			 .stringList(FileType.CONFIG, "config.server.motd")
			 .forEach(string -> player.sendMessage(IridiumColorAPI.process(string)));
	}
	
	private void executeGroupParticles(@NotNull Player player) {
		final ParticleShowEvent particleShowEvent = new ParticleShowEvent(
			 player,
			 this.particleManager
					.userParticleType(player.getUniqueId())
					.name()
		);
		this.plugin
			 .getServer()
			 .getPluginManager()
			 .callEvent(particleShowEvent);
		if (!particleShowEvent.isCancelled()) {
			if (this.configuration.check(
				 FileType.CONFIG,
				 "config.server.groups." + group + ".allow-particles")
			) {
				this.particleManager
					 .showForm(
							player,
							this.particleManager.userParticleType(player.getUniqueId()),
							this.configuration.string(
								 FileType.CONFIG,
								 "config.server.groups." + group + ".particle-params")
					 );
			}
		}
	}
}
