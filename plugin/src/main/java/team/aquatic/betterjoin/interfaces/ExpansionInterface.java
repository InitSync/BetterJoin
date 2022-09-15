package team.aquatic.betterjoin.interfaces;

import team.aquatic.betterjoin.handlers.ExpansionHandlerModel;

public interface ExpansionInterface {
	static ExpansionHandlerModel newInstance() {
		return new ExpansionHandlerModel();
	}
}
