package team.aquatic.betterjoin.utils.actions;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface Action {
	void sound(@NotNull Player player, @NotNull String container);
	
	void effect(@NotNull Player player, @NotNull String container);
	
	void title(@NotNull Player player, @NotNull String container);
	
	void actionbar(@NotNull Player player, @NotNull String container);
	
	void firework(@NotNull Player player, @NotNull String container);
	
	void command(@NotNull Player player, @NotNull String container);
	
	void broadcast(@NotNull Collection<? extends Player> players, @NotNull String container);
	
	void message(@NotNull Player player, @NotNull String container);
	
	void console(@NotNull Player player, @NotNull String container);
	
	void execute(@NotNull Player player, @NotNull String container);
}
