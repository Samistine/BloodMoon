package com.github.hexosse.bloodmoon.feature.mob;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityLiving;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class MovementSpeedListener extends PluginListener<BloodMoon>
{
    private final Random random = new Random();

    public MovementSpeedListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event)
    {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(worldConfig.FEATURE_MOVEMENT_SPEED_ENABLED.getValue())
        {
            for(LivingEntity entity : world.getLivingEntities())
            {
                if(worldConfig.FEATURE_MOVEMENT_SPEED_MOBS.getValue().contains(entity.getType().name()))
                {
                    try
                    {
                        double multiplier = (this.random.nextInt(100) < worldConfig.FEATURE_MOVEMENT_SPEED_FAST_CHANCE.getValue()) ? worldConfig.FEATURE_MOVEMENT_SPEED_FAST_MULTIPLIER.getValue() : worldConfig.FEATURE_MOVEMENT_SPEED_MULTIPLIER.getValue();

                        BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
                        bloodMoonEntity.setSpeedMultiplier(multiplier);
                    }
                    catch(IllegalArgumentException e) {
                        // This means the entity is not supported *shrug*
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event)
    {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_MOVEMENT_SPEED_ENABLED.getValue()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                try {
                    BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
                    bloodMoonEntity.clearSpeedMultiplier();
                } catch (IllegalArgumentException e) {
                    // This means the entity is not supported *shrug*
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        LivingEntity entity = event.getEntity();
        World world = entity.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(plugin.isActive(world) && worldConfig.FEATURE_MOVEMENT_SPEED_ENABLED.getValue())
        {
            if(worldConfig.FEATURE_MOVEMENT_SPEED_MOBS.getValue().contains(entity.getType().name()))
            {
                try
                {
                    BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
                    double multiplier = (this.random.nextInt(100) < worldConfig.FEATURE_MOVEMENT_SPEED_FAST_CHANCE.getValue()) ? worldConfig.FEATURE_MOVEMENT_SPEED_FAST_MULTIPLIER.getValue() : worldConfig.FEATURE_MOVEMENT_SPEED_MULTIPLIER.getValue();
                    bloodMoonEntity.setSpeedMultiplier(multiplier);
                }
                catch(IllegalArgumentException e)  {
                    // This means the entity is not supported *shrug*
                }
            }
        }
    }
}
