package com.github.hexosse.bloodmoon.feature.spawning;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.bloodmoon.tasks.GiantsTask;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.HashMap;

public class GiantsListener extends PluginListener<BloodMoon>
{
    private final HashMap<String, Integer> worldTasks = new HashMap<>();

    public GiantsListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world =  event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);
        String worldName = world.getName();

        if (worldConfig.FEATURE_GIANTS_ENABLED.getValue()) {
            int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new GiantsTask(plugin, world), 0L, 100L);

            if (taskID != -1) {
                this.worldTasks.put(worldName, taskID);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEnd(BloodMoonEndEvent event) {
        World world = event.getWorld();
        String worldName = world.getName();

        Integer taskID = this.worldTasks.get(worldName);

        if (taskID != null) {
            plugin.getServer().getScheduler().cancelTask(taskID);
            this.worldTasks.remove(worldName);

            for (LivingEntity entity : event.getWorld().getLivingEntities()) {
                if (entity.getType() == EntityType.GIANT) {
                    entity.remove();
                }
            }
        }
    }
}
