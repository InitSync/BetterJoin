package team.aquatic.betterjoin.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ActionsExecuteEvent extends Event implements Cancellable {
	private final HandlerList handlers;
	
	private boolean cancelled;
	
	public ActionsExecuteEvent() {
		this.handlers = new HandlerList();
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
