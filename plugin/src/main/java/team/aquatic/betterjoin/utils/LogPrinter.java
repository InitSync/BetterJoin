package team.aquatic.betterjoin.utils;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogPrinter {
	private static final Logger LOGGER = BetterJoin.instance().getLogger();
	
	public static void info(@NotNull String... strings) {
		for (String string : strings) {
			LOGGER.log(Level.INFO, string);
		}
	}
	
	public static void config(@NotNull String... strings) {
		for (String string : strings) {
			LOGGER.log(Level.CONFIG, string);
		}
	}
	
	public static void warn(@NotNull String... strings) {
		for (String string : strings) {
			LOGGER.log(Level.WARNING, string);
		}
	}
	
	public static void error(@NotNull String... strings) {
		for (String string : strings) {
			LOGGER.log(Level.SEVERE, string);
		}
	}
}
