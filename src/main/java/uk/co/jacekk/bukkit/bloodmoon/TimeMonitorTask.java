package uk.co.jacekk.bukkit.bloodmoon;

import java.util.Random;

import org.bukkit.World;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.scheduler.BaseTask;

public class TimeMonitorTask extends BaseTask<BloodMoon> {
	
	private Random random;
	
	public TimeMonitorTask(BloodMoon plugin){
		super(plugin);
		
		this.random = new Random();
	}
	
	@Override
	public void run(){
		for (World world : plugin.server.getWorlds()){
			String worldName = world.getName();
			
			if (plugin.isEnabled(worldName)){
				PluginConfig worldConfig = plugin.getConfig(worldName);
				
				if (!worldConfig.getBoolean(Config.ALWAYS_ON)){
					long worldTime = world.getTime();
					
					if (worldTime >= 13000 && worldTime <= 13100){
						if (plugin.forceWorlds.contains(worldName) || this.random.nextInt(100) < worldConfig.getInt(Config.CHANCE)){
							if (!plugin.isActive(worldName)){
								plugin.activate(worldName);
							}
							
							plugin.forceWorlds.remove(worldName);
						}
					}else if (worldTime >= 23000 && worldTime <= 23100){
						if (plugin.isActive(worldName)){
							plugin.deactivate(worldName);
						}
					}
				}
			}
		}
	}
	
}
