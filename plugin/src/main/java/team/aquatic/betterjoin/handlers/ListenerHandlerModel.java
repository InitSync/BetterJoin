package team.aquatic.betterjoin.handlers;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ListenerHandlerModel {
	private ListenerHandlerModel() {}
	
	public static class Builder {
		private final JavaPlugin plugin;
		
		private List<Listener> listeners;
		
		public Builder(@NotNull JavaPlugin plugin) {
			this.plugin = Objects.requireNonNull(plugin, "JavaPlugin instance is null.");
		}
		
		public Builder listener(@NotNull List<Listener> listeners) {
			this.listeners = Objects.requireNonNull(listeners, "The listeners list is null.");
			return this;
		}
		
		public void register() {
			this.listeners.forEach(listener -> {
				this.plugin
					 .getServer()
					 .getPluginManager()
					 .registerEvents(listener, this.plugin);
			});
		}
	}
}
