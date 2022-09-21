package team.aquatic.betterjoin.interfaces;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.managers.ParticleManager;

public interface ParticleInterface {
	/**
	 * This function returns a new instance of the ParticleManager class, which is a class that is used to
	 * manage the particles that are displayed when a player joins the server.
	 *
	 * @param plugin The plugin instance.
	 * @return A new instance of the ParticleManager class.
	 */
	static ParticleManager newManagerInstance(@NotNull BetterJoin plugin) {
		return new ParticleManager(plugin);
	}
}
