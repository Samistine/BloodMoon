package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class GiantsListener extends BaseListener<BloodMoon> {
	
	private HashMap<String, Integer> worldTasks;
	
	public GiantsListener(BloodMoon plugin){
		super(plugin);
		
		this.worldTasks = new HashMap<String, Integer>();
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStart(BloodMoonStartEvent event){
		CraftWorld world = (CraftWorld) event.getWorld();
		String worldName = world.getName();
		
		if (plugin.isFeatureEnabled(world.getName(), Feature.GIANTS)){
			int taskID = plugin.scheduler.scheduleSyncRepeatingTask(plugin, new GiantsTask(plugin, world), 0L, 100L);
			
			if (taskID != -1){
				this.worldTasks.put(worldName, taskID);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEnd(BloodMoonEndEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		
		Integer taskID = this.worldTasks.get(worldName);
		
		if (taskID != null){
			plugin.scheduler.cancelTask(taskID);
			this.worldTasks.remove(worldName);
			
			for (LivingEntity entity : event.getWorld().getLivingEntities()){
				if (entity.getType() == EntityType.GIANT){
					entity.remove();
				}
			}
		}
	}
	
}
