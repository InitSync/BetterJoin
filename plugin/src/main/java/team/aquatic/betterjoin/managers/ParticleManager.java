package team.aquatic.betterjoin.managers;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.Configuration;
import team.aquatic.betterjoin.enums.modules.particles.ParticleType;
import team.aquatic.betterjoin.particles.ParticleExecutable;
import team.aquatic.betterjoin.particles.forms.*;

import java.util.*;

public class ParticleManager {
	private final BetterJoin plugin;
	private final Map<ParticleType, ParticleExecutable> particles;
	private final Configuration configuration;
	
	public ParticleManager(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.particles = new HashMap<>();
		this.configuration = this.plugin.configuration();
		this.loadParticlesClasses();
	}
	
	/**
	 * It registers all the particle forms
	 */
	private void loadParticlesClasses() {
		this.registerParticles(
			 new CircleForm(),
			 new AtomForm(),
			 new CloudForm(),
			 new ConeForm(),
			 new DiamondForm(),
			 new ExplosionForm(),
			 new HeartForm(),
			 new LineForm(),
			 new PolygonForm()
		);
	}
	
	/**
	 * It takes an array of ParticleExecutable objects and adds them to the particles HashMap
	 */
	private void registerParticles(@NotNull ParticleExecutable... particleExecutables) {
		Arrays.asList(particleExecutables).forEach(this::registerParticle);
	}
	
	/**
	 * It takes a particle executable and puts it into a map with the form type as the key
	 *
	 * @param particleExecutable The particle executable to register.
	 */
	private void registerParticle(@NotNull ParticleExecutable particleExecutable) {
		Objects.requireNonNull(particleExecutable, "The particle executable implementation is null.");
		
		this.particles.put(particleExecutable.formType(), particleExecutable);
	}
	
	/**
	 * It returns the particle type of the player with the given uuid
	 *
	 * @param uuid The uuid of the player.
	 * @return The particle type of the user.
	 */
	public @Nullable ParticleType groupParticleType(@NotNull UUID uuid) {
		Objects.requireNonNull(uuid, "The uuid is null.");
		
		final String group = this.plugin
			 .groupManager()
			 .getPlayerGroup(uuid);
		return this.configuration.section("config.server.groups." + group) != null
			 ? ParticleType.valueOf(this.configuration.string("config.server.groups." + group + ".particle-type"))
			 : null;
	}
	
	/**
	 * It shows a form to the player, which allows them to choose a particle effect
	 *
	 * @param player The player to show the form to.
	 * @param type The type of particle you want to show the form for.
	 * @param params The parameters that the player entered.
	 */
	public void showForm(@NotNull Player player, @NotNull ParticleType type, @NotNull String params) {
		Objects.requireNonNull(player, "The player is null.");
		Objects.requireNonNull(type, "The particle type is null.");
		Validate.notEmpty(params, "The params is empty.");
		
		this.particles
			 .get(type)
			 .showForm(this.plugin, player, params);
	}
	
	/**
	 * It removes all particles from the particle system
	 */
	public void unregisterAll() {
		this.particles.clear();
	}
}
