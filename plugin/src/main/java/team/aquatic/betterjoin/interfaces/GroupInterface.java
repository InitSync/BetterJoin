package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.managers.GroupManager;

public interface GroupInterface {
	/**
	 * Create a new instance of GroupManager, and return it.
	 *
	 * @param plugin The plugin instance.
	 * @return A new instance of GroupManager.
	 */
	static GroupManager newManagerInstance(@NotNull BetterJoin plugin) {
		return new GroupManager(plugin);
	}
}
