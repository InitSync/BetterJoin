package team.aquatic.betterjoin.particles;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.modules.particles.ParticleType;

public abstract class ParticleExecutable {
	public abstract @NotNull ParticleType formType();
	
	public abstract void showForm(
		 @NotNull BetterJoin plugin,
		 @NotNull Player player,
		 @NotNull String params
	);
}
