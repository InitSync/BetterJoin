package team.aquatic.betterjoin.utils.actions;

import com.cryptomorin.xseries.XPotion;
import com.cryptomorin.xseries.XSound;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import team.aquatic.betterjoin.BetterJoin;
import team.aquatic.betterjoin.enums.modules.actions.ActionType;
import team.aquatic.betterjoin.utils.LogPrinter;
import team.aquatic.betterjoin.utils.Utils;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class ActionModelFactory implements Action {
	private final BetterJoin plugin;
	
	private String[] split;
	
	public ActionModelFactory(@NotNull BetterJoin plugin) {
		this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
	}
	
	@Override
	public void sound(@NotNull Player player, @NotNull String container) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(container, "The action container is empty.");
		
		container = container.substring(8);
		
		this.split = container.split(";", 3);
		
		final Optional<XSound> soundOptional = XSound.matchXSound(this.split[0]);
		if (!soundOptional.isPresent()) {
			LogPrinter.error("The sound for the action is not present at the container.");
			return;
		}
		
		final Sound bukkitSound = soundOptional.get().parseSound();
		assert bukkitSound != null;
		
		int volume;
		int pitch;
		try {
			volume = Integer.parseInt(this.split[1]);
			pitch = Integer.parseInt(this.split[2]);
		} catch (NumberFormatException exception) {
			LogPrinter.error("Failed to parse the sound action parameters.");
			
			exception.printStackTrace();
			return;
		}
		
		player.playSound(
			 player.getLocation(),
			 bukkitSound,
			 volume, pitch
		);
	}
	
	@Override
	public void effect(@NotNull Player player, @NotNull String container) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(container, "The action container is empty.");
		
		container = container.substring(9);
		
		this.split = container.split(";", 3);
		
		final Optional<XPotion> potionOptional = XPotion.matchXPotion(this.split[0]);
		if (!potionOptional.isPresent()) {
			LogPrinter.error("The effect for the action is not present at the container.");
			return;
		}
		
		final PotionEffectType effectType = potionOptional.get().getPotionEffectType();
		assert effectType != null;
		
		int duration;
		int amplifier;
		try {
			duration = Integer.parseInt(this.split[1]);
			amplifier = Integer.parseInt(this.split[2]);
		} catch (NumberFormatException exception) {
			LogPrinter.error("Failed to parse the effect action parameters.");
			
			exception.printStackTrace();
			return;
		}
		
		player.addPotionEffect(new PotionEffect(effectType, duration, amplifier));
	}
	
	@Override
	public void title(@NotNull Player player, @NotNull String container) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(container, "The action container is empty.");
		
		container = container.substring(8);
		
		this.split = container.split(";", 5);
		
		int fadeIn;
		int stay;
		int fadeOut;
		try {
			fadeIn = Integer.parseInt(this.split[2]);
			stay = Integer.parseInt(this.split[3]);
			fadeOut = Integer.parseInt(this.split[4]);
		} catch (NumberFormatException exception) {
			LogPrinter.error("Failed to parse the title action parameters.");
			
			exception.printStackTrace();
			return;
		}
		
		Utils.sendTitle(
			 player,
			 this.split[0],
			 this.split[1],
			 fadeIn,
			 stay,
			 fadeOut
		);
	}
	
	@Override
	public void actionbar(@NotNull Player player, @NotNull String container) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(container, "The action container is empty.");
		
		container = container.substring(12);
		
		this.split = container.split(";", 2);
		
		Utils.sendActionBar(
			 this.plugin,
			 player,
			 this.split[0],
			 Long.parseLong(this.split[1])
		);
	}
	
	@Override
	public void firework(@NotNull Player player, @NotNull String container) {
	
	}
	
	@Override
	public void command(@NotNull Player player, @NotNull String container) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(container, "The action container is empty.");
		
		container = container.substring(10);
		
		this.split = container.split(";", 2);
		
		final String command = IridiumColorAPI.process(this.split[0]);
		
		if (Boolean.parseBoolean(this.split[1])) {
			if (this.plugin
				 .getServer()
				 .isPrimaryThread()
			) {
				this.plugin
					 .getServer()
					 .dispatchCommand(
							Bukkit.getConsoleSender(),
						  command
					 );
			} else {
				new BukkitRunnable() {
					@Override
					public void run() {
						plugin.getServer()
							 .dispatchCommand(
									Bukkit.getConsoleSender(),
									command
							 );
						cancel();
					}
				}.runTaskLater(this.plugin, 1L);
			}
			return;
		}
		
		if (this.plugin
			 .getServer()
			 .isPrimaryThread()
		) {
			this.plugin
				 .getServer()
				 .dispatchCommand(player, command);
		} else {
			new BukkitRunnable() {
				@Override
				public void run() {
					plugin.getServer().dispatchCommand(player, command);
					cancel();
				}
			}.runTaskLater(this.plugin, 1L);
		}
	}
	
	@Override
	public void broadcast(@NotNull Collection<? extends Player> players, @NotNull String container) {
		Objects.requireNonNull(players, "Players collection is null.");
		Validate.notEmpty(container, "The action container is empty.");
		
		players.forEach(player -> {
			player.sendMessage(IridiumColorAPI.process(
				 container.substring(12)
			));
		});
	}
	
	@Override
	public void message(@NotNull Player player, @NotNull String container) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(container, "The action container is empty.");
		
		player.sendMessage(IridiumColorAPI.process(
			 container.substring(10)
		));
	}
	
	@Override
	public void console(@NotNull Player player, @NotNull String container) {
		Objects.requireNonNull(player, "Player is null.");
		Validate.notEmpty(container, "The action container is empty.");
		
		this.plugin
			 .getServer()
			 .getConsoleSender()
			 .sendMessage(IridiumColorAPI.process(
					container.substring(10)
			 ));
	}
	
	@Override
	public void execute(@NotNull Player player, @NotNull String container) {
		if (container.startsWith(ActionType.SOUND.getIdentifier())) {
			this.sound(player, container);
			return;
		}
		
		if (container.startsWith(ActionType.EFFECT.getIdentifier())) {
			this.effect(player, container);
			return;
		}
		
		if (container.startsWith(ActionType.TITLE.getIdentifier())) {
			this.title(player, container);
			return;
		}
		
		if (container.startsWith(ActionType.ACTION_BAR.getIdentifier())) {
			this.actionbar(player, container);
			return;
		}
		
		if (container.startsWith(ActionType.FIREWORK.getIdentifier())) {
			this.firework(player, container);
			return;
		}
		
		if (container.startsWith(ActionType.COMMAND.getIdentifier())) {
			this.command(player, container);
			return;
		}
		
		if (container.startsWith(ActionType.BROADCAST.getIdentifier())) {
			this.broadcast(
				 this.plugin
					  .getServer()
					  .getOnlinePlayers(),
				 container
			);
			return;
		}
		
		if (container.startsWith(ActionType.MESSAGE.getIdentifier())) {
			this.message(player, container);
			return;
		}
		
		if (container.startsWith(ActionType.CONSOLE.getIdentifier())) {
			this.console(player, container);
			return;
		}
		
		LogPrinter.error("The action: '" + container + "' doesn't exist or isn't valid!");
	}
}
