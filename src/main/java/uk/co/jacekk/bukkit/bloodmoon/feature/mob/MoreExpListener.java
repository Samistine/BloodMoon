package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public class MoreExpListener implements Listener {

    private final BloodMoon plugin;

    public MoreExpListener(BloodMoon plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        String worldName = entity.getWorld().getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (entity instanceof Creature && plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.MORE_EXP)) {
            if (!worldConfig.getBoolean(Config.FEATURE_MORE_EXP_IGNORE_SPAWNERS) || plugin.getSpawnReason(entity) != SpawnReason.SPAWNER) {
                event.setDroppedExp(event.getDroppedExp() * Math.max(worldConfig.getInt(Config.FEATURE_MORE_EXP_MULTIPLIER), 0));
            }
        }
    }

}
