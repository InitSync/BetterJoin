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
import team.aquatic.betterjoin.enums.modules.actions.ActionType;
import team.aquatic.betterjoin.enums.modules.files.FileType;
import team.aquatic.betterjoin.interfaces.ActionInterface;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;

public class PlayerJoinListener implements Listener {
	private final BetterJoin plugin;
	private final Configuration configuration;
	
	private String message;
	private String group;
	
	public PlayerJoinListener(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		this.group = this.plugin
			 .luckPerms()
			 .getUserManager()
			 .getUser(player.getUniqueId())
			 .getPrimaryGroup();
		
		if (this.configuration.check(FileType.CONFIG, "config.server.allow-motd")) {
			this.configuration
				 .stringList(FileType.CONFIG, "config.server.motd")
				 .forEach(string -> player.sendMessage(IridiumColorAPI.process(string)));
		}
		
		final UserServerJoinEvent serverJoinEvent = new UserServerJoinEvent();
		this.plugin
			 .getServer()
			 .getPluginManager()
			 .callEvent(serverJoinEvent);
		if (!serverJoinEvent.isCancelled()) {
			if (this.configuration.section(FileType.CONFIG, "config.server.groups." + group) != null) {
				this.message = this.configuration.string(
					 FileType.CONFIG,
					 "config.server.groups." + group + ".join"
				);
			} else {
				this.message = null;
				event.setJoinMessage("");
				return;
			}
			
			event.setJoinMessage(Utils.parse(player, message));
			
			if (this.configuration.check(
				 FileType.CONFIG,
				 "config.server.groups." + group + ".allow-particles")
			) {
				XParticle.circle(5, 1, ParticleDisplay.display(player.getLocation(), Particle.ENCHANTMENT_TABLE));
			}
			
			this.configuration
				 .stringList(FileType.CONFIG,
						"config.server.groups." + group + ".actions")
				 .forEach(action -> {
					 ActionInterface.newAction(this.plugin)
							.toPlayer(player)
							.action(ActionType.valueOf(action.split(";")[0]))
							.container(action.replace(ActionType.valueOf(action) + ";", ""))
							.make();
				 });
		}
	}
}
