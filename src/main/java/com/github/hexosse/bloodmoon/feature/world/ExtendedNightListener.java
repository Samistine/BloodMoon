package com.github.hexosse.bloodmoon.feature.world;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class ExtendedNightListener extends PluginListener<BloodMoon>
{

    private final HashMap<String, Integer> killCount = new HashMap<>();
    private final ArrayList<EntityType> hostileTypes = new ArrayList<EntityType>() {
        {
            add(EntityType.SKELETON);
            add(EntityType.SPIDER);
            add(EntityType.CAVE_SPIDER);
            add(EntityType.ZOMBIE);
            add(EntityType.PIG_ZOMBIE);
            add(EntityType.CREEPER);
            add(EntityType.ENDERMAN);
            add(EntityType.BLAZE);
            add(EntityType.GHAST);
            add(EntityType.MAGMA_CUBE);
            add(EntityType.WITCH);
            add(EntityType.ENDERMITE);
            add(EntityType.ENDER_DRAGON);
            add(EntityType.GUARDIAN);
            add(EntityType.SILVERFISH);
        }
    };

    public ExtendedNightListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_EXTENDED_NIGHT_ENABLED.getValue()) {
            world.setGameRuleValue("doDaylightCycle", "false");

            if (!worldConfig.ALWAYS_ON.getValue()) {
                killCount.put(world.getName(), 0);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_EXTENDED_NIGHT_ENABLED.getValue()) {
            killCount.remove(world.getName());
            world.setGameRuleValue("doDaylightCycle", "true");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (hostileTypes.contains(event.getEntityType())) {
            World world = event.getEntity().getWorld();
            String worldName = world.getName();
            WorldConfig worldConfig = plugin.getConfig(world);

            if (worldConfig.FEATURE_EXTENDED_NIGHT_ENABLED.getValue() && killCount.containsKey(worldName)) {
                killCount.put(worldName, killCount.get(worldName) + 1);

                if (killCount.get(worldName) > worldConfig.FEATURE_EXTENDED_NIGHT_MIN_KILLS.getValue()) {
                    world.setGameRuleValue("doDaylightCycle", "true");
                }
            }
        }
    }

}
