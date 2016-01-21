package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBedEnterEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.baseplugin.util.ListUtils;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.integrations.Factions;

public final class SpawnOnSleepListener extends BaseListener<BloodMoon> {

    public SpawnOnSleepListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        World world = event.getPlayer().getWorld();
        String worldName = world.getName();

        if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.SPAWN_ON_SLEEP)) {
            PluginConfig worldConfig = plugin.getConfig(worldName);
            String mobName = ListUtils.getRandom(worldConfig.getStringList(Config.FEATURE_SPAWN_ON_SLEEP_SPAWN));
            EntityType creatureType = EntityType.fromName(mobName.toUpperCase());

            if (creatureType != null && plugin.spawnEntityAllowed(creatureType, event.getPlayer().getLocation())) {
                //world.spawn(location, creatureType.getEntityClass(), SpawnReason.NATURAL);
                world.spawnEntity(event.getPlayer().getLocation(), creatureType);
            }
        }
    }

}
