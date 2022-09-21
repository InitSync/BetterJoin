package team.aquatic.betterjoin.managers;

import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.enums.modules.actions.ActionType;
import team.aquatic.betterjoin.enums.modules.files.FileType;
import team.aquatic.betterjoin.interfaces.ActionInterface;

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
	
	public String getPlayerGroup(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		return this.luckPerms
			 .getUserManager()
			 .getUser(uuid)
			 .getPrimaryGroup();
	}
	
	public String groupJoinMessage(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		final String group = this.getPlayerGroup(uuid);
		
		String message;
		if (this.configuration.section(FileType.CONFIG, "config.server.groups." + group) != null) {
			message = this.configuration.string(
				 FileType.CONFIG,
				 "config.server.groups." + group + ".join");
			return message;
		}
		
		message = "";
		return message;
	}
	
	public String groupQuitMessage(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		final String group = this.getPlayerGroup(uuid);
		
		String message;
		if (this.configuration.section(FileType.CONFIG, "config.server.groups." + group) != null) {
			message = this.configuration.string(
				 FileType.CONFIG,
				 "config.server.groups." + group + ".quit");
			return message;
		}
		
		message = "";
		return message;
	}
	
	public void executeGroupActions(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		final String group = this.getPlayerGroup(player.getUniqueId());
		if (this.configuration.section(FileType.CONFIG, "config.server.groups." + group) != null) {
			this.configuration
				 .stringList(FileType.CONFIG, "config.server.groups." + group + ".actions")
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
