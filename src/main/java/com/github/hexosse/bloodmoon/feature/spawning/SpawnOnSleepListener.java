package com.github.hexosse.bloodmoon.feature.spawning;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.util.RandomUtils;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBedEnterEvent;

public final class SpawnOnSleepListener extends PluginListener<BloodMoon>
{
    public SpawnOnSleepListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerBedEnter(PlayerBedEnterEvent event)
    {
        World world = event.getPlayer().getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_SPAWN_ON_SLEEP_ENABLED.getValue()) {
            String mobName = RandomUtils.getRandom(worldConfig.FEATURE_SPAWN_ON_SLEEP_SPAWN.getValue());
            EntityType creatureType = EntityType.fromName(mobName.toUpperCase());

            if (creatureType != null && plugin.spawnEntityAllowed(creatureType, event.getPlayer().getLocation())) {
                //world.spawn(location, creatureType.getEntityClass(), SpawnReason.NATURAL);
                world.spawnEntity(event.getPlayer().getLocation(), creatureType);
            }
        }
    }
}
