package team.aquatic.betterjoin.interfaces;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.handlers.CommandHandlerModel;
import team.aquatic.betterjoin.handlers.ListenerHandlerModel;

public interface LoadersInterface {
	/**
	 * It creates a new instance of the CommandHandlerModel.Builder class
	 *
	 * @param plugin The plugin instance.
	 * @return A new instance of the CommandHandlerModel.Builder class.
	 */
	static CommandHandlerModel.Builder newCommand(@NotNull JavaPlugin plugin) {
		return new CommandHandlerModel.Builder(plugin);
	}
	
	/**
	 * Create a new instance of the ListenerHandlerModel.Builder class, and pass the plugin instance to
	 * the constructor.
	 *
	 * @param plugin The plugin instance.
	 * @return A new instance of the ListenerHandlerModel.Builder class.
	 */
	static ListenerHandlerModel.Builder newListener(@NotNull JavaPlugin plugin) {
		return new ListenerHandlerModel.Builder(plugin);
	}
}
