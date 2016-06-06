package com.github.hexosse.bloodmoon.feature.spawning;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityType;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.Random;

public final class MoreSpawningListener extends PluginListener<BloodMoon>
{
    private final Random random = new Random();

    public MoreSpawningListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != SpawnReason.NATURAL) { //TODO: Should check for Custom instead of checking against natural?
            return;
        }

        EntityType type = event.getEntityType();
        World world = event.getLocation().getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_MORE_SPAWNING_ENABLED.getValue() && worldConfig.FEATURE_MORE_SPAWNING_MOBS.getValue().contains(type.getName().toUpperCase())) {
            for (int i = 0; i < Math.max(worldConfig.FEATURE_MORE_SPAWNING_MULTIPLIER.getValue(), 1); ++i) {
                for (BloodMoonEntityType bloodMoonEntity : BloodMoonEntityType.values()) {
                    if (type == bloodMoonEntity.getEntityType()) {
                        Location loc = event.getLocation().add((random.nextDouble() * 3) - 1.5, (random.nextDouble() * 3) - 1.5, (random.nextDouble() * 3) - 1.5);
                        if(plugin.spawnEntityAllowed(type, loc))
                            bloodMoonEntity.spawnEntity(loc);
                        return;
                    }
                }
            }
        }
    }
}
