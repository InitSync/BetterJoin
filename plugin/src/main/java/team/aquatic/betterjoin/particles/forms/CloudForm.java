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

public class CloudForm extends ParticleExecutable {
	private String[] split;
	
	@Override
	public @NotNull ParticleType formType() {
		return ParticleType.CLOUD;
	}
	
	@Override
	public void showForm(@NotNull BetterJoin plugin, @NotNull Player player, @NotNull String params) {
		Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		Objects.requireNonNull(player, "The player is null.");
		Validate.notEmpty(params, "The particle params is empty.");
		
		this.split = params.split(";");
		
		final Particle cloudParticle = Particle.valueOf(this.split[0]);
		final Particle rainParticle = Particle.valueOf(this.split[1]);
		XParticle.cloud(plugin,
			 ParticleDisplay.of(cloudParticle),
			 ParticleDisplay.of(rainParticle)
		);
	}
}
