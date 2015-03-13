package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import java.util.Random;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.GenericAttributes;
import org.bukkit.Bukkit;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class MovementSpeedListener extends BaseListener<BloodMoon> {
	
	private Random random;
	
	public MovementSpeedListener(BloodMoon plugin){
		super(plugin);
		
		this.random = new Random();
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStart(BloodMoonStartEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.MOVEMENT_SPEED)){
			for (LivingEntity entity : world.getLivingEntities()){
				if (worldConfig.getStringList(Config.FEATURE_MOVEMENT_SPEED_MOBS).contains(entity.getType().name())){
					try{
						BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
						double multiplier = worldConfig.getDouble((this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_MOVEMENT_SPEED_FAST_CHANCE)) ? Config.FEATURE_MOVEMENT_SPEED_FAST_MULTIPLIER : Config.FEATURE_MOVEMENT_SPEED_MULTIPLIER);
						bloodMoonEntity.setSpeedMultiplier(multiplier);
					}catch (IllegalArgumentException e){
						// This means the entity is not supported *shrug*
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStop(BloodMoonEndEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		
		if (plugin.isFeatureEnabled(worldName, Feature.MOVEMENT_SPEED)){
			for (LivingEntity entity : world.getLivingEntities()){
				try{
					BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle()).clearSpeedMultiplier();
				}catch (IllegalArgumentException e){
					// This means the entity is not supported *shrug*
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
		
		if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.MOVEMENT_SPEED)){
			if (worldConfig.getStringList(Config.FEATURE_MOVEMENT_SPEED_MOBS).contains(entity.getType().name())){
				try{
					BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
                                        //EntityInsentient sam = (EntityInsentient)((CraftLivingEntity) entity).getHandle();
                                        //System.err.println(bloodMoonEntity.toString() + "le mob");
					double multiplier = worldConfig.getDouble((this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_MOVEMENT_SPEED_FAST_CHANCE)) ? Config.FEATURE_MOVEMENT_SPEED_FAST_MULTIPLIER : Config.FEATURE_MOVEMENT_SPEED_MULTIPLIER);
					bloodMoonEntity.setSpeedMultiplier(multiplier);
				}catch (IllegalArgumentException e){
					// This means the entity is not supported *shrug*
				}
			}
		}
	}
	
}
