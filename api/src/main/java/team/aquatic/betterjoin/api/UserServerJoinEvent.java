package team.aquatic.betterjoin.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserServerJoinEvent extends Event implements Cancellable {
	private final HandlerList handlers;
	
	private boolean cancellable;
	private String joinMessage;
	
	public UserServerJoinEvent() {
		this.handlers = new HandlerList();
	}
	
	public @Nullable String getJoinMessage() {
		return this.joinMessage;
	}
	
	public void setJoinMessage(@Nullable String joinMessage) {
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
