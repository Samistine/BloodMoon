package com.github.hexosse.bloodmoon.feature.spawning;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public final class MoreMobsListener extends PluginListener<BloodMoon>
{
    public MoreMobsListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        for (LivingEntity entity : world.getLivingEntities()) {
            if (worldConfig.FEATURE_SPAWN_CONTROL_SPAWN.getValue().contains(entity.getType().name())) {
                entity.remove();
            }
        }
    }
}
