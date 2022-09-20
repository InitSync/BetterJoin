package team.aquatic.betterjoin.handlers;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ListenerHandlerModel {
	private ListenerHandlerModel() {}
	
	public static class Builder {
		private final JavaPlugin plugin;
		
		private Listener listener;
		
		public Builder(@NotNull JavaPlugin plugin) {
			this.plugin = Objects.requireNonNull(plugin, "JavaPlugin instance is null.");
		}
		
		/**
		 * Sets the listener to the given listener.
		 *
		 * @param listener The listener to be called when the event is fired.
		 * @return The builder itself.
		 */
		public Builder event(@NotNull Listener listener) {
			this.listener = Objects.requireNonNull(listener, "The listener is null.");
			return this;
		}
		
		/**
		 * Register the listener with the plugin manager.
		 *
		 * @return The builder object.
		 */
		public Builder register() {
			this.plugin
				 .getServer()
				 .getPluginManager()
				 .registerEvents(this.listener, this.plugin);
			return this;
		}
	}
}
