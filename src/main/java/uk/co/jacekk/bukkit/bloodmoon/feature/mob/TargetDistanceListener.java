package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityLiving;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class TargetDistanceListener implements Listener {

    private final BloodMoon plugin;

    public TargetDistanceListener(BloodMoon plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isFeatureEnabled(world, Feature.TARGET_DISTANCE)) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (worldConfig.getStringList(Config.FEATURE_TARGET_DISTANCE_MOBS).contains(entity.getType().name())) {
                    try {
                        BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
                        bloodMoonEntity.setFollowRangeMultiplier(worldConfig.getDouble(Config.FEATURE_TARGET_DISTANCE_MULTIPLIER));
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

        if (plugin.isFeatureEnabled(world, Feature.TARGET_DISTANCE)) {
            for (LivingEntity entity : world.getLivingEntities()) {
                try {
                    BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle()).clearFollowRangeMultiplier();
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
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.TARGET_DISTANCE)) {
            if (worldConfig.getStringList(Config.FEATURE_TARGET_DISTANCE_MOBS).contains(entity.getType().name())) {
                try {
                    //((CraftLivingEntity)entity).setfol
                    BloodMoonEntityLiving bloodMoonEntity = BloodMoonEntityLiving.getBloodMoonEntity(((CraftLivingEntity) entity).getHandle());
                    bloodMoonEntity.setFollowRangeMultiplier(worldConfig.getDouble(Config.FEATURE_TARGET_DISTANCE_MULTIPLIER));
                } catch (IllegalArgumentException e) {
                    // This means the entity is not supported *shrug*
                }
            }
        }
    }

}
