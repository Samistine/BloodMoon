package uk.co.jacekk.bukkit.bloodmoon.feature.server;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public class DisabledCommandsListener extends BaseListener<BloodMoon> {
	
	public DisabledCommandsListener(BloodMoon plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		Player player = event.getPlayer();
		String message = event.getMessage();
		String worldName = player.getWorld().getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		int spaceIndex = message.indexOf(' ');
		
		String command = (spaceIndex > 0) ? message.substring(1, spaceIndex) : message.substring(1);
		
		if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.DISABLED_COMMANDS)){
			if (worldConfig.getStringList(Config.FEATURE_DISABLED_COMMANDS_COMMANDS).contains(command)){
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "The /" + command + " is disabled during a bloodmoon!");
			}
		}
	}
	
}
