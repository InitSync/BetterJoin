package team.aquatic.betterjoin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;

import java.util.Objects;

public class MainCommand implements CommandExecutor {
	private final BetterJoin plugin;
	
	public MainCommand(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
	}
	
	@Override
	public boolean onCommand(
		 @NotNull CommandSender sender,
		 @NotNull Command command,
		 @NotNull String label,
		 @NotNull String[] args
	) {
		return false;
	}
}
