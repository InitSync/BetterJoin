package team.aquatic.betterjoin.handlers;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandHandlerModel {
	private CommandHandlerModel() {}
	
	public static class Builder {
		private final JavaPlugin plugin;
		
		private String commandName;
		private CommandExecutor executor;
		private TabCompleter completer;
		private PluginCommand command;
		
		public Builder(@NotNull JavaPlugin plugin) {
			this.plugin = Objects.requireNonNull(plugin, "JavaPlugin instance is null.");
		}
		
		public Builder name(@NotNull String name) {
			Validate.notEmpty(name, "The name is empty.");
			
			this.commandName = name;
			return this;
		}
		
		public Builder executor(@NotNull CommandExecutor commandExecutor) {
			this.executor = Objects.requireNonNull(commandExecutor, "The executor is null.");
			return this;
		}
		
		public Builder completer(@NotNull TabCompleter tabCompleter) {
			this.completer = Objects.requireNonNull(tabCompleter, "The tab completer is null.");
			return this;
		}
		
		public Builder register() {
			this.command = this.plugin.getCommand(this.commandName);
			assert this.command != null;
			
			this.command.setExecutor(this.executor);
			this.command.setTabCompleter(this.completer);
			return this;
		}
	}
}
