package team.aquatic.betterjoin.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.enums.modules.files.FileActionType;
import team.aquatic.betterjoin.enums.modules.permissions.PermissionType;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Objects;

public class MainCommand implements CommandExecutor {
	private final BetterJoin plugin;
	private final Configuration configuration;
	private final String prefix;
	
	public MainCommand(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
		this.prefix = this.configuration.string("config.prefix");
	}
	
	@Override
	public boolean onCommand(
		 @NotNull CommandSender sender,
		 @NotNull Command command,
		 @NotNull String label,
		 @NotNull String[] args
	) {
		if (!(sender instanceof Player)) return false;
		
		final Player player = (Player) sender;
		
		if (args.length == 0) {
			player.sendMessage(MiniMessage.miniMessage().deserialize(
				 "<prefix> &f Running at &8(&b" + Bukkit.getBukkitVersion() + "&8)",
				 Placeholder.parsed("prefix", this.prefix))
			);
			player.sendMessage(MiniMessage.miniMessage().deserialize(
				 "<prefix> &f Developed by &b" + this.plugin
						.author + " &8| &a" + this.plugin
						.version,
				 Placeholder.parsed("prefix", this.prefix))
			);
			return false;
		}
		
		switch (args[0]) {
			default -> {
				player.sendMessage(Utils.parse(player,
					 this.configuration.string("messages.no-command")
						  .replace("<prefix>", this.prefix))
				);
			}
			case "help" -> {
				if (player.hasPermission(PermissionType.HELP_CMD.getPerm())) {
					this.configuration
						 .stringList("messages.help")
						 .forEach(string -> {
							 player.sendMessage(Utils.parse(player, string.replace("<prefix>", this.prefix)));
						 });
				} else this.notPermission(player);
			}
			case "config" -> {
				if (player.hasPermission(PermissionType.RELOAD_CMD.getPerm())) {
					this.reload(player);
					return false;
				}
				this.notPermission(player);
			}
		}
		return false;
	}
	
	private void notPermission(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		final int volume = this.configuration.integer("config.sounds.volume-level");
		
		player.playSound(
			 player.getLocation(),
			 Sound.valueOf(this.configuration.string("config.sounds.no-perm")),
			 volume, volume
		);
		player.sendMessage(MiniMessage.miniMessage().deserialize(
			 this.configuration
				  .string("messages.no-permission")
				  .replace("<prefix>", this.prefix)
		));
	}
	
	private void reload(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		this.configuration.doSomething(FileActionType.RELOAD);
		
		final int volume = this.configuration.integer("config.sounds.volume-level");
		
		player.playSound(
			 player.getLocation(),
			 Sound.valueOf(this.configuration.string("config.sounds.reload")),
			 volume, volume
		);
		player.sendMessage(MiniMessage.miniMessage().deserialize(
			 this.configuration
					.string("messages.reload")
					.replace("<prefix>", this.prefix)
		));
	}
}
