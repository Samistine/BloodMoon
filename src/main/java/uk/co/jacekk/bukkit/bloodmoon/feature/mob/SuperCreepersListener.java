package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftCreeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class SuperCreepersListener extends BaseListener<BloodMoon> {
	
	public SuperCreepersListener(BloodMoon plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStart(BloodMoonStartEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.SUPER_CREEPERS) && worldConfig.getBoolean(Config.FEATURE_SUPER_CREEPERS_LIGHTNING)){
			for (LivingEntity entity : world.getLivingEntities()){
				if (entity.getType() == EntityType.CREEPER){
					((CraftCreeper) entity).setPowered(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStop(BloodMoonEndEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.SUPER_CREEPERS) && worldConfig.getBoolean(Config.FEATURE_SUPER_CREEPERS_LIGHTNING)){
			for (LivingEntity entity : world.getLivingEntities()){
				if (entity.getType() == EntityType.CREEPER){
					((CraftCreeper) entity).setPowered(false);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		LivingEntity entity = event.getEntity();
		World world = entity.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (entity.getType() == EntityType.CREEPER && plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.SUPER_CREEPERS) && worldConfig.getBoolean(Config.FEATURE_SUPER_CREEPERS_LIGHTNING)){
			((CraftCreeper) entity).setPowered(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onExplosionProme(ExplosionPrimeEvent event){
		if (event.getEntity() != null && event.getEntityType() == EntityType.CREEPER){
			String worldName = event.getEntity().getWorld().getName();
			PluginConfig worldConfig = plugin.getConfig(worldName);
			
			if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.SUPER_CREEPERS)){
				event.setRadius((float) worldConfig.getDouble(Config.FEATURE_SUPER_CREEPERS_POWER));
				event.setFire(worldConfig.getBoolean(Config.FEATURE_SUPER_CREEPERS_FIRE));
			}
		}
	}
	
}
