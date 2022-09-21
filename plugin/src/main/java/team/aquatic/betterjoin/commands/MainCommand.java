package team.aquatic.betterjoin.commands;

import com.cryptomorin.xseries.XSound;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
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
import team.aquatic.betterjoin.enums.modules.files.FileType;
import team.aquatic.betterjoin.enums.modules.permissions.PermissionType;

import java.util.Objects;

public class MainCommand implements CommandExecutor {
	private final BetterJoin plugin;
	private final Configuration configuration;
	private final Sound permSound;
	private final Sound reloadSound;
	private final String prefix;
	
	public MainCommand(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.configuration = this.plugin.configuration();
		this.permSound = XSound.matchXSound(this.configuration.string(
			 FileType.CONFIG,
			 "config.sounds.no-perm")
		).get().parseSound();
		this.reloadSound = XSound.matchXSound(this.configuration.string(
			 FileType.CONFIG,
			 "config.sounds.reload")
		).get().parseSound();
		this.prefix = this.configuration.string(FileType.CONFIG, "config.prefix");
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
			player.sendMessage(IridiumColorAPI.process(
				 this.prefix + "&f Running at &8(&b" + Bukkit.getBukkitVersion() + "&8)"
			));
			player.sendMessage(IridiumColorAPI.process(
				 this.prefix + "&f Developed by &b" + this.plugin
					  .author + " &8| &a" + this.plugin
					  .version
			));
			return false;
		}
		
		switch (args[0]) {
			default:
				player.sendMessage(IridiumColorAPI.process(
					 this.configuration
						  .string(FileType.MESSAGES, "messages.no-command")
						  .replace("<prefix>", this.prefix)
				));
				break;
			case "help":
				if (player.hasPermission(PermissionType.HELP_CMD.getPerm())) {
					this.configuration
						 .stringList(FileType.MESSAGES, "messages.help")
						 .forEach(string -> sender.sendMessage(IridiumColorAPI.process(string)));
				} else this.notPermission(player);
				break;
			case "reload":
				if (player.hasPermission(PermissionType.HELP_CMD.getPerm())) this.reload(player);
				else this.notPermission(player);
				break;
		}
		return false;
	}
	
	private void notPermission(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		final int volume = this.configuration.integer(FileType.CONFIG, "config.sounds.volume-level");
		
		player.playSound(
			 player.getLocation(),
			 this.permSound,
			 volume, volume
		);
		player.sendMessage(IridiumColorAPI.process(
			 this.configuration
				  .string(FileType.MESSAGES, "messages.no-permission")
				  .replace("<prefix>", this.prefix)
		));
	}
	
	private void reload(@NotNull Player player) {
		Objects.requireNonNull(player, "The player is null.");
		
		this.configuration.doSomething(FileType.CONFIG, FileActionType.RELOAD);
		this.configuration.doSomething(FileType.MESSAGES, FileActionType.RELOAD);
		
		final int volume = this.configuration.integer(FileType.CONFIG, "config.sounds.volume-level");
		
		player.playSound(
			 player.getLocation(),
			 this.reloadSound,
			 volume, volume
		);
		player.sendMessage(IridiumColorAPI.process(
			 this.configuration
				  .string(FileType.MESSAGES, "messages.reload")
				  .replace("<prefix>", this.prefix)
		));
	}
}
