package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class MaxHealthListener implements Listener {

    private final BloodMoon plugin;
    private final String metaKey = "Bloodmoon-" + getClass().getSimpleName();

    public MaxHealthListener(BloodMoon plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isFeatureEnabled(world, Feature.MAX_HEALTH)) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (worldConfig.getStringList(Config.FEATURE_MAX_HEALTH_MOBS).contains(entity.getType().name())) {
                    doEntity(entity, worldConfig);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        World world = event.getLocation().getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.MAX_HEALTH) && worldConfig.getStringList(Config.FEATURE_MAX_HEALTH_MOBS).contains(entity.getType().name())) {
            doEntity(entity, worldConfig);
        }
    }

    private void doEntity(LivingEntity entity, PluginConfig worldConfig) {
        double newMaxHealth = entity.getMaxHealth() * worldConfig.getDouble(Config.FEATURE_MAX_HEALTH_MULTIPLIER);
        double damage = entity.getMaxHealth() - entity.getHealth();

        entity.setMaxHealth(newMaxHealth);
        entity.setHealth(Math.min(newMaxHealth - damage, newMaxHealth));

        entity.setMetadata(metaKey, new FixedMetadataValue(plugin, true));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isFeatureEnabled(world, Feature.MAX_HEALTH)) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (worldConfig.getStringList(Config.FEATURE_MAX_HEALTH_MOBS).contains(entity.getType().name())) {
                    if (entity.hasMetadata(metaKey)) {
                        entity.resetMaxHealth();
                    }
                }
            }
        }
    }

}
