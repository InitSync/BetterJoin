package team.aquatic.betterjoin.interfaces;

import team.aquatic.betterjoin.handlers.ExpansionHandlerModel;

public interface ExpansionInterface {
	/**
	 * > This function creates a new instance of the `ExpansionHandlerModel` class
	 *
	 * @return A new instance of the ExpansionHandlerModel class.
	 */
	static ExpansionHandlerModel newExpansionInstance() {
		return new ExpansionHandlerModel();
	}
}
