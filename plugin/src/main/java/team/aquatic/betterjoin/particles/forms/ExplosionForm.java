package team.aquatic.betterjoin.particles.forms;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.modules.particles.ParticleType;
import team.aquatic.betterjoin.particles.ParticleExecutable;

import java.util.Objects;

public class ExplosionForm extends ParticleExecutable {
	private String[] split;
	
	@Override
	public @NotNull ParticleType formType() {
		return ParticleType.EXPLOSION;
	}
	
	@Override
	public void showForm(@NotNull BetterJoin plugin, @NotNull Player player, @NotNull String params) {
		Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		Objects.requireNonNull(player, "The player is null.");
		Validate.notEmpty(params, "The particle params is empty.");
		
		this.split = params.split(";");
		
		final double rate = Double.parseDouble(this.split[0]);
		
		final Particle particle = Particle.valueOf(this.split[1]);
		final Particle secParticle = Particle.valueOf(this.split[2]);
		
		final Location location = player.getLocation();
		XParticle.explosionWave(
			 plugin,
			 rate,
			 ParticleDisplay.display(location, particle),
			 ParticleDisplay.display(location, secParticle)
		);
	}
}
