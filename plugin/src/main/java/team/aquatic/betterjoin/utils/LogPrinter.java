package team.aquatic.betterjoin.utils;

import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogPrinter {
	private static final Logger LOGGER = BetterJoin.instance().getLogger();
	
	/**
	 * > This function takes a variable number of strings and logs them at the INFO level
	 */
	public static void info(@NotNull String... strings) {
		for (String string : strings) {
			LOGGER.log(Level.INFO, string);
		}
	}
	
	/**
	 * If the logger is enabled for the CONFIG level, then log the strings.
	 */
	public static void config(@NotNull String... strings) {
		for (String string : strings) {
			LOGGER.log(Level.CONFIG, string);
		}
	}
	
	/**
	 * Logs a warning message to the console.
	 */
	public static void warn(@NotNull String... strings) {
		for (String string : strings) {
			LOGGER.log(Level.WARNING, string);
		}
	}
	
	/**
	 * It takes a variable number of strings, and logs them all as errors
	 */
	public static void error(@NotNull String... strings) {
		for (String string : strings) {
			LOGGER.log(Level.SEVERE, string);
		}
	}
}
