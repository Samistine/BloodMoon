package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public class MoreDropsListener implements Listener {

    private final BloodMoon plugin;

    public MoreDropsListener(BloodMoon plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        String worldName = entity.getWorld().getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (entity instanceof Creature && plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.MORE_DROPS)) {
            if (!worldConfig.getBoolean(Config.FEATURE_MORE_DROPS_IGNORE_SPAWNERS) || plugin.getSpawnReason(entity) != SpawnReason.SPAWNER) {
                for (ItemStack drop : event.getDrops()) {
                    drop.setAmount(drop.getAmount() * Math.max(worldConfig.getInt(Config.FEATURE_MORE_DROPS_MULTIPLIER), 0));
                }
            }
        }
    }

}
