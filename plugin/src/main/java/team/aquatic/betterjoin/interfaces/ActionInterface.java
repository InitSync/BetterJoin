package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.managers.ActionManager;

public interface ActionInterface {
	/**
	 * This function returns a new instance of the ActionManager class.
	 *
	 * @param plugin The plugin instance.
	 * @return A new instance of the ActionManager class.
	 */
	static ActionManager newManagerInstance(@NotNull BetterJoin plugin) {
		return new ActionManager(plugin);
	}
}
