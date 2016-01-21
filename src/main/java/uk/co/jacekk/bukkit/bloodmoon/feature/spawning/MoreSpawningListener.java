package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;
import uk.co.jacekk.bukkit.bloodmoon.integrations.Factions;

public final class MoreSpawningListener extends BaseListener<BloodMoon> {

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
        String worldName = event.getLocation().getWorld().getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.MORE_SPAWNING) && worldConfig.getStringList(Config.FEATURE_MORE_SPAWNING_MOBS).contains(type.getName().toUpperCase())) {
            for (int i = 0; i < Math.max(worldConfig.getInt(Config.FEATURE_MORE_SPAWNING_MULTIPLIER), 1); ++i) {
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
