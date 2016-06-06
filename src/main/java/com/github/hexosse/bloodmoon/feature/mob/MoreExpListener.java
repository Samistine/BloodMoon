package com.github.hexosse.bloodmoon.feature.mob;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

public class MoreExpListener extends PluginListener<BloodMoon>
{
    public MoreExpListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        World world = entity.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (entity instanceof Creature && plugin.isActive(world) && worldConfig.FEATURE_MORE_EXP_ENABLED.getValue()) {
            if (!worldConfig.FEATURE_MORE_EXP_IGNORE_SPAWNERS.getValue() || plugin.getSpawnReason(entity) != SpawnReason.SPAWNER) {
                event.setDroppedExp(event.getDroppedExp() * Math.max(worldConfig.FEATURE_MORE_EXP_MULTIPLIER.getValue(), 0));
            }
        }
    }
}
