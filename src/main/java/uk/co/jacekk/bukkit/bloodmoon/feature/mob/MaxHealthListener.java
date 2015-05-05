package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class MaxHealthListener implements Listener {
	
        private final BloodMoon plugin;
    
	public MaxHealthListener(BloodMoon plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onStart(BloodMoonStartEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.MAX_HEALTH)){
			for (LivingEntity entity : world.getLivingEntities()){
				if (worldConfig.getStringList(Config.FEATURE_MAX_HEALTH_MOBS).contains(entity.getType().name())){
					double newMaxHealth = entity.getMaxHealth() * worldConfig.getDouble(Config.FEATURE_MAX_HEALTH_MULTIPLIER);
					double damage = entity.getMaxHealth() - entity.getHealth();
					
					entity.setMaxHealth(newMaxHealth);
					entity.setHealth(Math.min(newMaxHealth - damage, newMaxHealth));
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		LivingEntity entity = event.getEntity();
		String worldName = event.getLocation().getWorld().getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.MAX_HEALTH) && worldConfig.getStringList(Config.FEATURE_MAX_HEALTH_MOBS).contains(entity.getType().name())){
			double newMaxHealth = entity.getMaxHealth() * worldConfig.getDouble(Config.FEATURE_MAX_HEALTH_MULTIPLIER);
			double damage = entity.getMaxHealth() - entity.getHealth();
			
			entity.setMaxHealth(newMaxHealth);
			entity.setHealth(Math.min(newMaxHealth - damage, newMaxHealth));
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onStop(BloodMoonEndEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.MAX_HEALTH)){
			for (LivingEntity entity : world.getLivingEntities()){
				if (worldConfig.getStringList(Config.FEATURE_MAX_HEALTH_MOBS).contains(entity.getType().name())){
					entity.resetMaxHealth();
				}
			}
		}
	}
	
}
