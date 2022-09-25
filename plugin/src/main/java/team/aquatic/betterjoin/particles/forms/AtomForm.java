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

public class AtomForm extends ParticleExecutable {
	private String[] split;
	
	@Override
	public @NotNull ParticleType formType() {
		return ParticleType.ATOM;
	}
	
	@Override
	public void showForm(@NotNull BetterJoin plugin, @NotNull Player player, @NotNull String params) {
		Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		Objects.requireNonNull(player, "The player is null.");
		Validate.notEmpty(params, "The particle params is empty.");
		
		this.split = params.split(";");
		
		final int orbitsNumber = Integer.parseInt(this.split[0]);
		
		final double radius = Double.parseDouble(this.split[1]);
		final double rate = Double.parseDouble(this.split[2]);
		
		final Particle orbitParticle = Particle.valueOf(this.split[3]);
		final Particle nucleusParticle = Particle.valueOf(this.split[4]);
		
		final Location location = player.getLocation();
		XParticle.atom(
			 orbitsNumber, radius, rate,
			 ParticleDisplay.display(location, orbitParticle),
			 ParticleDisplay.display(location, nucleusParticle)
		);
	}
}
