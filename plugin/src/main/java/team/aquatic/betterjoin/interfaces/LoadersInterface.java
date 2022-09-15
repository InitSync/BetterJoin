package team.aquatic.betterjoin.interfaces;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.handlers.CommandHandlerModel;
import team.aquatic.betterjoin.handlers.ListenerHandlerModel;

public interface LoadersInterface {
	static CommandHandlerModel.Builder newCommand(@NotNull JavaPlugin plugin) {
		return new CommandHandlerModel.Builder(plugin);
	}
	
	static ListenerHandlerModel.Builder newEvent(@NotNull JavaPlugin plugin) {
		return new ListenerHandlerModel.Builder(plugin);
	}
}
