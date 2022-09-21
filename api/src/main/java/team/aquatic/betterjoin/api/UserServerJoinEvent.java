package team.aquatic.betterjoin.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UserServerJoinEvent extends Event implements Cancellable {
	private final HandlerList handlers;
	
	private boolean cancellable;
	private String joinMessage;
	
	public UserServerJoinEvent() {
		this.handlers = new HandlerList();
	}
	
	/**
	 * `getJoinMessage()` returns the join message of the player
	 *
	 * @return A string
	 */
	public @NotNull String getJoinMessage() {
		return this.joinMessage;
	}
	
	/**
	 * `setJoinMessage` sets the join message
	 *
	 * @param joinMessage The message to send when a player joins the server.
	 */
	public void setJoinMessage(@NotNull String joinMessage) {
		this.joinMessage = joinMessage;
	}
	
	@Override
	public boolean isCancelled() {
		return this.cancellable;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		this.cancellable = cancel;
	}
	
	@Override
	public @NotNull HandlerList getHandlers() {
		return this.handlers;
	}
}
