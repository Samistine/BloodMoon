package uk.co.jacekk.bukkit.bloodmoon.feature.server;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class ServerCommandsListener extends BaseListener<BloodMoon> {
	
	public ServerCommandsListener(BloodMoon plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBloodMoonStart(BloodMoonStartEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.SERVER_COMMANDS)){
			for (String command : worldConfig.getStringList(Config.FEATURE_SERVER_COMMANDS_START_COMMANDS)){
				plugin.server.dispatchCommand(plugin.server.getConsoleSender(), command);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBloodMoonStop(BloodMoonEndEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.SERVER_COMMANDS)){
			for (String command : worldConfig.getStringList(Config.FEATURE_SERVER_COMMANDS_END_COMMANDS)){
				plugin.server.dispatchCommand(plugin.server.getConsoleSender(), command);
			}
		}
	}
	
}
