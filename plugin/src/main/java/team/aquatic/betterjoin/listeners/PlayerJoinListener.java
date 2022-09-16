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
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.enums.modules.files.FileType;

import java.util.Objects;

public class PlayerJoinListener implements Listener {
	private final BetterJoin plugin;
	private final Configuration configuration;
	
	public PlayerJoinListener(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onJoin(@NotNull PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		if (this.configuration.check(FileType.CONFIG, "config.server.allow-motd")) {
			this.configuration
				 .stringList(FileType.CONFIG, "config.server.motd")
				 .forEach(string -> player.sendMessage(IridiumColorAPI.process(string)));
		}
		
		XParticle.circle(5, 1, ParticleDisplay.display(player.getLocation(), Particle.ENCHANTMENT_TABLE));
	}
}
