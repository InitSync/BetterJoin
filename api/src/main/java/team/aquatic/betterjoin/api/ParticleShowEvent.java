package team.aquatic.betterjoin.api;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ParticleShowEvent extends Event implements Cancellable {
	private final HandlerList handlers;
	private final Player player;
	private final String particleType;
	
	private boolean cancelled;
	
	public ParticleShowEvent(@NotNull Player player, @NotNull String particleType) {
		this.handlers = new HandlerList();
		this.player = Objects.requireNonNull(player, "The player is null.");
		
		Validate.notEmpty(particleType, "The particle type is empty or null.");
		this.particleType = particleType;
	}
	
	/**
	 * `Returns the player that this event is associated with.`
	 *
	 * @return The player object.
	 */
	public @NotNull Player player() {
		return this.player;
	}
	
	/**
	 * `getType()` returns the type of the particle
	 *
	 * @return The particle type.
	 */
	public @NotNull String getType() {
		return this.particleType;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
	
	@Override
	public @NotNull HandlerList getHandlers() {
		return this.handlers;
	}
}
