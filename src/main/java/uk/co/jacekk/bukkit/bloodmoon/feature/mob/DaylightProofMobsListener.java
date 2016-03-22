package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public class DaylightProofMobsListener implements Listener {

    private final BloodMoon plugin;

    public DaylightProofMobsListener(BloodMoon plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityCombust(EntityCombustEvent event) {
        World world = event.getEntity().getWorld();
        EntityType type = event.getEntityType();

        if (type == EntityType.ZOMBIE || type == EntityType.SKELETON) {
            if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.DAYLIGHT_PROOF_MOBS)) {
                event.setCancelled(true);
            }
        }

    }

}


//Class score 10/10
