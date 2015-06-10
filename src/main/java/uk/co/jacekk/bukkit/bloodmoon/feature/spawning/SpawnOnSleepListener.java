package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerBedEnterEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.baseplugin.util.ListUtils;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public class SpawnOnSleepListener extends BaseListener<BloodMoon> {

    public SpawnOnSleepListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Location location = event.getPlayer().getLocation();
        CraftWorld world = (CraftWorld) location.getWorld();
        String worldName = world.getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.SPAWN_ON_SLEEP)) {
            String mobName = ListUtils.getRandom(worldConfig.getStringList(Config.FEATURE_SPAWN_ON_SLEEP_SPAWN));
            EntityType creatureType = EntityType.fromName(mobName.toUpperCase());

            if (creatureType != null) {
                world.spawn(location, creatureType.getEntityClass(), SpawnReason.NATURAL);
            }
        }
    }

}
