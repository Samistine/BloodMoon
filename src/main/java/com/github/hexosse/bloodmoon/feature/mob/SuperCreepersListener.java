package com.github.hexosse.bloodmoon.feature.mob;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class SuperCreepersListener extends PluginListener<BloodMoon>
{
    public SuperCreepersListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_SUPER_CREEPERS_ENABLED.getValue() && worldConfig.FEATURE_SUPER_CREEPERS_LIGHTNING.getValue()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (entity.getType() == EntityType.CREEPER) {
                    ((Creeper) entity).setPowered(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_SUPER_CREEPERS_ENABLED.getValue() && worldConfig.FEATURE_SUPER_CREEPERS_LIGHTNING.getValue()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (entity.getType() == EntityType.CREEPER) {
                    ((Creeper) entity).setPowered(false);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        World world = entity.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (entity.getType() == EntityType.CREEPER && plugin.isActive(world) && worldConfig.FEATURE_SUPER_CREEPERS_ENABLED.getValue() && worldConfig.FEATURE_SUPER_CREEPERS_LIGHTNING.getValue()) {
            ((Creeper) entity).setPowered(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (event.getEntity() != null && event.getEntityType() == EntityType.CREEPER) {
            World world = event.getEntity().getWorld();
            WorldConfig worldConfig = plugin.getConfig(world);

            if (plugin.isActive(world) && worldConfig.FEATURE_SUPER_CREEPERS_ENABLED.getValue()) {
                event.setRadius(worldConfig.FEATURE_SUPER_CREEPERS_POWER.getValue());
                event.setFire(worldConfig.FEATURE_SUPER_CREEPERS_FIRE.getValue());
            }
        }
    }
}
