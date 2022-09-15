package team.aquatic.betterjoin.interfaces;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.handlers.ListenerHandlerModel;

public interface ListenerInterface {
	static ListenerHandlerModel.Builder newEvent(@NotNull JavaPlugin plugin) {
		return new ListenerHandlerModel.Builder(plugin);
	}
}
