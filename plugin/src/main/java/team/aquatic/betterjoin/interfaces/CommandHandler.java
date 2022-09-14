package team.aquatic.betterjoin.interfaces;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.handlers.CommandHandlerModel;

public interface CommandHandler {
	static CommandHandlerModel.Builder newCommand(@NotNull JavaPlugin plugin) {
		return new CommandHandlerModel.Builder(plugin);
	}
}
