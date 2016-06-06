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
import org.bukkit.inventory.ItemStack;

public class MoreDropsListener extends PluginListener<BloodMoon>
{
    public MoreDropsListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        World world = entity.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (entity instanceof Creature && plugin.isActive(world) && worldConfig.FEATURE_MORE_DROPS_ENABLED.getValue()) {
            if (!worldConfig.FEATURE_MORE_DROPS_IGNORE_SPAWNERS.getValue() || plugin.getSpawnReason(entity) != SpawnReason.SPAWNER) {
                for (ItemStack drop : event.getDrops()) {
                    drop.setAmount(drop.getAmount() * Math.max(worldConfig.FEATURE_MORE_DROPS_MULTIPLIER.getValue(), 0));
                }
            }
        }
    }
}
