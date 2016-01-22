package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;

public final class MoreMobsListener extends BaseListener<BloodMoon> {

    public MoreMobsListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        for (LivingEntity entity : world.getLivingEntities()) {
            if (worldConfig.getStringList(Config.FEATURE_SPAWN_CONTROL_SPAWN).contains(entity.getType().name())) {
                entity.remove();
            }
        }
    }

}
