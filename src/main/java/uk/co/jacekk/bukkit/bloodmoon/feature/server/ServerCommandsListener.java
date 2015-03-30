package uk.co.jacekk.bukkit.bloodmoon.feature.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.scheduler.BukkitScheduler;

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
				if (command.startsWith("(")) {
					String mydata = command;
					Pattern pattern = Pattern.compile("^(\\(([^()]*)\\))");
					Matcher matcher = pattern.matcher(mydata);
					if (matcher.find())
					{ //found ()||(x) at beginning of line
						String group2 = matcher.group(2); //group 2 should be just x without ()
						if (!group2.isEmpty()) { //check if it is empty
							int time;
							try {
								time = Integer.parseInt(group2);
							} catch(NumberFormatException e) {
								plugin.getLogger().severe("ERROR PARSING INTEGER, in config, please fix line: " + command);
								return;
							}
							String replaceRegex = "//(" + time + "//)";
							String commandToRun = command.replaceFirst(replaceRegex, "");
							if (commandToRun.startsWith(" ")) {
								commandToRun = commandToRun.replaceFirst(" ", "");
							}
                                                        
                                                        final String finalCommand = commandToRun;
                                                        
							BukkitScheduler scheduler = plugin.getServer().getScheduler();
							scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                                                            
								@Override
								public void run() {
									plugin.server.dispatchCommand(plugin.server.getConsoleSender(), finalCommand);
								}
							}, time);
                                                        return;
						}
					}

				}
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
