package team.aquatic.betterjoin.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserServerQuitEvent extends Event implements Cancellable {
	private final HandlerList handlers;
	
	private boolean cancellable;
	private String quitMessage;
	
	public UserServerQuitEvent() {
		this.handlers = new HandlerList();
	}
	
	public @Nullable String getQuitMessage() {
		return this.quitMessage;
	}
	
	public void setQuitMessage(@Nullable String quitMessage) {
		this.quitMessage = quitMessage;
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
