package team.aquatic.betterjoin.managers;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.actions.ActionExecutable;
import team.aquatic.betterjoin.actions.types.*;
import team.aquatic.betterjoin.api.ActionsExecuteEvent;
import team.aquatic.betterjoin.enums.modules.actions.ActionType;

import java.util.*;

public class ActionManager {
	private final BetterJoin plugin;
	private final Map<ActionType, ActionExecutable> actions;
	
	public ActionManager(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		this.actions = new HashMap<>();
		this.loadActionsClasses();
	}
	
	/**
	 * It registers all the actions
	 */
	private void loadActionsClasses() {
		this.registerActions(
			 new SoundActionType(),
			 new EffectActionType(),
			 new TitleActionType(),
			 new ActionBarActionType(),
			 new CommandActionType(),
			 new BroadcastActionType(),
			 new MessageActionType(),
			 new ConsoleActionType()
		);
	}
	
	/**
	 * It takes an array of ActionExecutable objects and adds them to the actions HashMap
	 */
	private void registerActions(@NotNull ActionExecutable... actionExecutables) {
		Arrays.asList(actionExecutables).forEach(this::registerAction);
	}
	
	/**
	 * It takes a action executable and puts it into a map with the action type as the key
	 *
	 * @param actionExecutable The action executable to register.
	 */
	private void registerAction(@NotNull ActionExecutable actionExecutable) {
		Objects.requireNonNull(actionExecutable, "The action to register is null.");
		
		this.actions.put(actionExecutable.actionType(), actionExecutable);
	}
	
	/**
	 * It takes a player and a list of containers, and executes the actions inside of the containers
	 *
	 * @param player The player who is executing the command.
	 * @param containers The list of containers that are to be executed.
	 */
	public void executeActions(@NotNull Player player, @NotNull List<String> containers) {
		Objects.requireNonNull(player, "The player is null.");
		Objects.requireNonNull(containers, "The containers list is null.");
		
		final ActionsExecuteEvent executeEvent = new ActionsExecuteEvent();
		this.plugin
			 .getServer()
			 .getPluginManager()
			 .callEvent(executeEvent);
		if (!executeEvent.isCancelled()) {
			containers.forEach(container -> {
				final String actionPrefix = StringUtils.substringBetween(container, "[", "]");
				final ActionExecutable executable = this.actions.get(ActionType.valueOf(actionPrefix.toUpperCase()));
				
				executable.executeAction(this.plugin, player,
					 container.contains(" ")
							? container.split(" ", 2)[1]
							: ""
				);
			});
		}
	}
	
	/**
	 * It removes all actions from the actions system
	 */
	public void unregisterAll() { this.actions.clear(); }
}
