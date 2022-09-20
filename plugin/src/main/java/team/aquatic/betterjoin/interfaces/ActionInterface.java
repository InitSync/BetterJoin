package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.handlers.ActionFactoryHandler;

public interface ActionInterface {
	/**
	 * It creates a new ActionFactoryHandler.Builder object, which is used to create a new
	 * ActionFactoryHandler object
	 *
	 * @param plugin The plugin instance.
	 * @return A new ActionFactoryHandler.Builder object.
	 */
	static ActionFactoryHandler.Builder newAction(@NotNull BetterJoin plugin) {
		return new ActionFactoryHandler.Builder(plugin);
	}
}
