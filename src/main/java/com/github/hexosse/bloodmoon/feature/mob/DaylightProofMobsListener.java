package com.github.hexosse.bloodmoon.feature.mob;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityCombustEvent;

public class DaylightProofMobsListener extends PluginListener<BloodMoon>
{
    public DaylightProofMobsListener(BloodMoon plugin)
    {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityCombust(EntityCombustEvent event)
    {
        World world = event.getEntity().getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);
        EntityType type = event.getEntityType();

        if(type == EntityType.ZOMBIE || type == EntityType.SKELETON)
        {
            if(plugin.isActive(world) && worldConfig.FEATURE_DAYLIGHT_PROOF_MOBS_ENABLED.getValue())
            {
                event.setCancelled(true);
            }
        }

    }
}
