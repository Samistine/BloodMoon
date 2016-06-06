package com.github.hexosse.bloodmoon.feature.mob;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MaxHealthListener extends PluginListener<BloodMoon>
{
    private final String metaKey = "Bloodmoon-" + getClass().getSimpleName();

    public MaxHealthListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_MAX_HEALTH_ENABLED.getValue()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (worldConfig.FEATURE_MAX_HEALTH_MOBS.getValue().contains(entity.getType().name())) {
                    doEntity(entity, worldConfig);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        World world = event.getLocation().getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_MAX_HEALTH_ENABLED.getValue() && worldConfig.FEATURE_MAX_HEALTH_MOBS.getValue().contains(entity.getType().name())) {
            doEntity(entity, worldConfig);
        }
    }

    private void doEntity(LivingEntity entity, WorldConfig worldConfig) {
        double newMaxHealth = entity.getMaxHealth() * worldConfig.FEATURE_MAX_HEALTH_MULTIPLIER.getValue();
        double damage = entity.getMaxHealth() - entity.getHealth();

        entity.setMaxHealth(newMaxHealth);
        entity.setHealth(Math.min(newMaxHealth - damage, newMaxHealth));

        entity.setMetadata(metaKey, new FixedMetadataValue(plugin, true));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_MAX_HEALTH_ENABLED.getValue()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (worldConfig.FEATURE_MAX_HEALTH_MOBS.getValue().contains(entity.getType().name())) {
                    if (entity.hasMetadata(metaKey)) {
                        entity.resetMaxHealth();
                    }
                }
            }
        }
    }
}
