package team.aquatic.betterjoin.managers;

import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.api.ParticleShowEvent;
import team.aquatic.betterjoin.enums.Configuration;

import java.util.Objects;
import java.util.UUID;

public class GroupManager {
	private final BetterJoin plugin;
	private final Configuration configuration;
	private final LuckPerms luckPerms;
	private final ParticleManager particleManager;
	
	public GroupManager(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
		this.luckPerms = this.plugin.luckPerms();
		this.particleManager = this.plugin.particleManager();
	}
	
	public @Nullable String getPlayerGroup(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		return this.luckPerms
			 .getUserManager()
			 .getUser(uuid)
			 .getPrimaryGroup();
	}
	
	public @NotNull String groupJoinMessage(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		final String group = this.getPlayerGroup(uuid);
		
		String message;
		if (this.configuration.section("config.server.groups." + group) != null) {
			message = this.configuration.string("config.server.groups." + group + ".join");
			return message;
		}
		
		message = "";
		return message;
	}
	
	public @NotNull String groupQuitMessage(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		final String group = this.getPlayerGroup(uuid);
		
		String message;
		if (this.configuration.section("config.server.groups." + group) != null) {
			message = this.configuration.string("config.server.groups." + group + ".quit");
			return message;
		}
		
		message = "";
		return message;
	}
	
	public void executeGroupActions(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		final String group = this.getPlayerGroup(player.getUniqueId());
		if (this.configuration.section("config.server.groups." + group) != null) {
			this.plugin
				 .actionManager()
				 .executeActions(
						player,
					  this.configuration.stringList("config.server.groups." + group + ".actions")
				 );
		}
	}
	
	public void executeGroupParticles(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		final UUID uuid = player.getUniqueId();
		final ParticleShowEvent particleShowEvent = new ParticleShowEvent(
			 player,
			 this.particleManager.groupParticleType(uuid).name()
		);
		this.plugin
			 .getServer()
			 .getPluginManager()
			 .callEvent(particleShowEvent);
		if (!particleShowEvent.isCancelled()) {
			final String group = this.getPlayerGroup(player.getUniqueId());
			if (this.configuration.section("config.server.groups." + group) != null) {
				this.particleManager
					 .showForm(
							player,
							this.particleManager.groupParticleType(uuid),
							this.configuration.string("config.server.groups." + group + ".particle-params")
					 );
			}
		}
	}
}
