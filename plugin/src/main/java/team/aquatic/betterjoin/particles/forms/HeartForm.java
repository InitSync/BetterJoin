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

public class HeartForm extends ParticleExecutable {
	private String[] split;
	
	@Override
	public @NotNull ParticleType formType() {
		return ParticleType.HEART;
	}
	
	@Override
	public void showForm(@NotNull BetterJoin plugin, @NotNull Player player, @NotNull String params) {
		Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		Objects.requireNonNull(player, "The player is null.");
		Validate.notEmpty(params, "The particle params is empty.");
		
		this.split = params.split(";");
		
		final double cut = Double.parseDouble(this.split[0]);
		final double cutAngle = Double.parseDouble(this.split[1]);
		final double depth = Double.parseDouble(this.split[2]);
		final double compressHeight = Double.parseDouble(this.split[3]);
		final double rate = Double.parseDouble(this.split[4]);
		
		final Particle particle = Particle.valueOf(this.split[5]);
		XParticle.heart(
			 cut, cutAngle, depth,
			 compressHeight, rate,
			 ParticleDisplay.display(player.getLocation(), particle)
		);
	}
}
