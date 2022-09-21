package team.aquatic.betterjoin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCommandTabCompleter implements TabCompleter {
	private final List<String> commandArgs;
	
	public MainCommandTabCompleter() {
		this.commandArgs = new ArrayList<>();
	}
	
	@Override
	public @Nullable List<String> onTabComplete(
		 @NotNull CommandSender sender,
		 @NotNull Command command,
		 @NotNull String alias,
		 @NotNull String[] args
	) {
		if (this.commandArgs.isEmpty()) {
			this.commandArgs.add("help");
			this.commandArgs.add("reload");
		}
		
		final List<String> results = new ArrayList<>();
		
		if (args.length == 1) {
			for (String arg : this.commandArgs) {
				if (arg.toLowerCase().startsWith(args[0].toLowerCase())) results.add(arg);
			}
			return results;
		}
		return Collections.emptyList();
	}
}
