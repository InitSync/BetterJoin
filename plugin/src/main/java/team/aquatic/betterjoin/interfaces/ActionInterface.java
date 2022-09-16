package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.utils.actions.ActionModelFactory;

public interface ActionInterface {
	static ActionModelFactory newInstance(@NotNull BetterJoin plugin) {
		return new ActionModelFactory(plugin);
	}
}
