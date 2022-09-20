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
		
		public Builder(@NotNull JavaPlugin plugin) {
			this.plugin = Objects.requireNonNull(plugin, "JavaPlugin instance is null.");
		}
		
		/**
		 * This function sets the name of the command to the given name, and returns the builder.
		 *
		 * @param name The name of the command.
		 * @return The builder itself.
		 */
		public Builder name(@NotNull String name) {
			Validate.notEmpty(name, "The name is empty.");
			this.commandName = name;
			return this;
		}
		
		/**
		 * `executor` sets the executor of the command
		 *
		 * @param commandExecutor The command executor.
		 * @return The builder itself.
		 */
		public Builder executor(@NotNull CommandExecutor commandExecutor) {
			this.executor = Objects.requireNonNull(commandExecutor, "The executor is null.");
			return this;
		}
		
		/**
		 * Sets the tab completer for the command.
		 *
		 * @param tabCompleter The tab completer to use.
		 * @return The builder itself.
		 */
		public Builder completer(@NotNull TabCompleter tabCompleter) {
			this.completer = Objects.requireNonNull(tabCompleter, "The tab completer is null.");
			return this;
		}
		
		/**
		 * "Register the command with the plugin."
		 *
		 * The first line of the function is a comment. Comments are ignored by the compiler, and are used to
		 * explain what the code does
		 *
		 * @return The builder itself.
		 */
		public Builder register() {
			final PluginCommand command = this.plugin.getCommand(this.commandName);
			assert command != null;
			
			command.setExecutor(this.executor);
			command.setTabCompleter(this.completer);
			return this;
		}
	}
}
