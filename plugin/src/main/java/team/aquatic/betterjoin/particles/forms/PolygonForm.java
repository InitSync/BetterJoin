package team.aquatic.betterjoin.particles.forms;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import org.apache.commons.lang.Validate;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.modules.particles.ParticleType;
import team.aquatic.betterjoin.particles.ParticleExecutable;

import java.util.Objects;

public class PolygonForm extends ParticleExecutable {
	private String[] split;
	
	@Override
	public @NotNull ParticleType formType() {
		return ParticleType.POLYGON;
	}
	
	@Override
	public void showForm(@NotNull BetterJoin plugin, @NotNull Player player, @NotNull String params) {
		Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		Objects.requireNonNull(player, "The player is null.");
		Validate.notEmpty(params, "The particle params is empty.");
		
		this.split = params.split(";");
		
		final int points = Integer.parseInt(this.split[0]);
		final int connections = Integer.parseInt(this.split[1]);
		final double size = Double.parseDouble(this.split[2]);
		final double rate = Double.parseDouble(this.split[3]);
		final double extend = Double.parseDouble(this.split[4]);
		final Particle particle = Particle.valueOf(this.split[5]);
		XParticle.polygon(
			 points, connections,
			 size, rate, extend,
			 ParticleDisplay.of(particle)
		);
	}
}
