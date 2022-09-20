package team.aquatic.betterjoin.handlers;

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

import java.util.Objects;
import java.util.Optional;

public class ActionFactoryHandler {
	private ActionFactoryHandler() {}
	
	public static class Builder {
		private final BetterJoin plugin;
		
		private Player player;
		private ActionType actionType;
		private String[] splitter;
		private String container;
		
		public Builder(@NotNull BetterJoin plugin) {
			this.plugin = Objects.requireNonNull(plugin, "BetterJoin instance is null.");
		}
		
		public Builder toPlayer(@NotNull Player player) {
			this.player = Objects.requireNonNull(player, "The player is null.");
			return this;
		}
		
		public Builder action(@NotNull ActionType actionType) {
			this.actionType = Objects.requireNonNull(actionType, "The action type is null.");
			return this;
		}
		
		public Builder container(@NotNull String container) {
			Validate.notEmpty(container, "The container is empty.");
			this.container = container;
			return this;
		}
		
		public void make() {
			if (actionType == ActionType.SOUND) {
				this.container = this.container.substring(8);
				this.splitter = this.container.split(";", 3);
				
				final Optional<XSound> soundOptional = XSound.matchXSound(this.splitter[0]);
				if (!soundOptional.isPresent()) {
					LogPrinter.error("The sound for the action is not present at the container.");
					return;
				}
				
				final Sound bukkitSound = soundOptional.get().parseSound();
				assert bukkitSound != null;
				
				int volume;
				int pitch;
				try {
					volume = Integer.parseInt(this.splitter[1]);
					pitch = Integer.parseInt(this.splitter[2]);
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
				return;
			}
			
			if (actionType == ActionType.EFFECT) {
				this.container = this.container.substring(9);
				this.splitter = this.container.split(";", 3);
				
				final Optional<XPotion> potionOptional = XPotion.matchXPotion(this.splitter[0]);
				if (!potionOptional.isPresent()) {
					LogPrinter.error("The effect for the action is not present at the container.");
					return;
				}
				
				final PotionEffectType effectType = potionOptional.get().getPotionEffectType();
				assert effectType != null;
				
				int duration;
				int amplifier;
				try {
					duration = Integer.parseInt(this.splitter[1]);
					amplifier = Integer.parseInt(this.splitter[2]);
				} catch (NumberFormatException exception) {
					LogPrinter.error("Failed to parse the effect action parameters.");
					
					exception.printStackTrace();
					return;
				}
				
				player.addPotionEffect(new PotionEffect(effectType, duration, amplifier));
				return;
			}
			
			if (actionType == ActionType.TITLE) {
				this.container = this.container.substring(8);
				this.splitter = container.split(";", 5);
				
				int fadeIn;
				int stay;
				int fadeOut;
				try {
					fadeIn = Integer.parseInt(this.splitter[2]);
					stay = Integer.parseInt(this.splitter[3]);
					fadeOut = Integer.parseInt(this.splitter[4]);
				} catch (NumberFormatException exception) {
					LogPrinter.error("Failed to parse the title action parameters.");
					
					exception.printStackTrace();
					return;
				}
				
				Utils.sendTitle(
					 player,
					 this.splitter[0],
					 this.splitter[1],
					 fadeIn,
					 stay,
					 fadeOut
				);
				return;
			}
			
			if (actionType == ActionType.ACTION_BAR) {
				this.container = this.container.substring(12);
				this.splitter = this.container.split(";", 2);
				
				Utils.sendActionBar(
					 this.plugin,
					 player,
					 this.splitter[1],
					 Long.parseLong(this.splitter[0])
				);
				return;
			}
			
			if (actionType == ActionType.FIREWORK) {
				return;
			}
			
			if (actionType == ActionType.COMMAND) {
				this.container = this.container.substring(10);
				this.splitter = this.container.split(";", 2);
				final String command = IridiumColorAPI.process(this.splitter[0]);
				
				if (Boolean.parseBoolean(this.splitter[1])) {
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
						return;
					}
					
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
					return;
				}
				
				if (this.plugin
					 .getServer()
					 .isPrimaryThread()
				) {
					this.plugin
						 .getServer()
						 .dispatchCommand(player, command);
					return;
				}
				
				new BukkitRunnable() {
					@Override
					public void run() {
						plugin.getServer().dispatchCommand(player, command);
						cancel();
					}
				}.runTaskLater(this.plugin, 1L);
				return;
			}
			
			if (actionType == ActionType.BROADCAST) {
				this.plugin
					 .getServer()
					 .getOnlinePlayers()
					 .forEach(player1 -> {
						 player.sendMessage(IridiumColorAPI.process(
							  container.substring(12)
						 ));
					 });
				return;
			}
			
			if (actionType == ActionType.MESSAGE) {
				player.sendMessage(IridiumColorAPI.process(
					 container.substring(10)
				));
				return;
			}
			
			if (actionType == ActionType.CONSOLE) {
				this.plugin
					 .getServer()
					 .getConsoleSender()
					 .sendMessage(IridiumColorAPI.process(
							container.substring(10)
					 ));
			}
		}
	}
}
