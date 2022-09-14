package team.aquatic.betterjoin;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.utils.LogPrinter;

public final class BetterJoin extends JavaPlugin {
	private static BetterJoin instance;
	
	private final PluginDescriptionFile descriptionFile = this.getDescription();
	
	public final String author = String.join("", this.descriptionFile.getAuthors());
	public final String version = this.descriptionFile.getVersion();
	
	public static @NotNull BetterJoin instance() {
		if (instance == null) {
			throw new IllegalStateException("Cannot access to BetterJoin instance because is disabled!");
		}
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		LogPrinter.info("Started plugin successfully.");
	}
	
	@Override
	public void onDisable() {
		if (instance != null) {
			LogPrinter.info("Disabling plugin...");
			
			instance = null;
		}
	}
}
