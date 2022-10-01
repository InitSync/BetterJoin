package team.aquatic.betterjoin.managers;

import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;
import java.util.UUID;

public class GroupManager {
	private final BetterJoin plugin;
	private final Configuration configuration;
	private final LuckPerms luckPerms;
	
	public GroupManager(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
		this.luckPerms = this.plugin.luckPerms();
	}
	
	public @Nullable String getPlayerGroup(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		return this.luckPerms
			 .getUserManager()
			 .getUser(uuid)
			 .getPrimaryGroup();
	}
	
	public boolean isAllowedGroup(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		return this.configuration.check("config.server.groups." + this.getPlayerGroup(uuid) + ".allow");
	}
	
	public @NotNull Component groupJoinMessage(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		final String group = this.getPlayerGroup(player.getUniqueId());
	
		return Utils.parse(player,
			 this.configuration.section("config.server.groups." + group) != null
					? this.configuration.string("config.server.groups." + group + ".join")
					: ""
		);
	}
	
	public @NotNull Component groupQuitMessage(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		final String group = this.getPlayerGroup(player.getUniqueId());
		
		return Utils.parse(player,
			 this.configuration.section("config.server.groups." + group) != null
				  ? this.configuration.string("config.server.groups." + group + ".quit")
				  : ""
		);
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
}
