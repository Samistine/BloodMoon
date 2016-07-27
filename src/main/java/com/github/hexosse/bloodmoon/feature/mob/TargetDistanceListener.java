package com.github.hexosse.bloodmoon.feature.mob;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityLiving;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class TargetDistanceListener extends PluginListener<BloodMoon>
{
    public TargetDistanceListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_TARGET_DISTANCE_ENABLED.getValue()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (worldConfig.FEATURE_TARGET_DISTANCE_MOBS.getValue().contains(entity.getType().name())) {
                    try {
                        BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
                        bloodMoonEntity.setFollowRangeMultiplier(worldConfig.FEATURE_TARGET_DISTANCE_MULTIPLIER.getValue());
                    } catch (IllegalArgumentException e) {
                        // This means the entity is not supported *shrug*
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_TARGET_DISTANCE_ENABLED.getValue()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                try {
					BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
					bloodMoonEntity.clearFollowRangeMultiplier();
                } catch (IllegalArgumentException e) {
                    // This means the entity is not supported *shrug*
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        World world = entity.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_TARGET_DISTANCE_ENABLED.getValue()) {
            if (worldConfig.FEATURE_TARGET_DISTANCE_MOBS.getValue().contains(entity.getType().name())) {
                try {
                    BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
                    bloodMoonEntity.setFollowRangeMultiplier(worldConfig.FEATURE_TARGET_DISTANCE_MULTIPLIER.getValue());
                } catch (IllegalArgumentException e) {
                    // This means the entity is not supported *shrug*
                }
            }
        }
    }
}
