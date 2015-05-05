package uk.co.jacekk.bukkit.bloodmoon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class SpawnReasonListener implements Listener {
	
    private final BloodMoon plugin;
	public SpawnReasonListener(BloodMoon plugin){
            this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		event.getEntity().setMetadata("spawn-reason", new FixedMetadataValue(plugin, event.getSpawnReason()));
	}
	
}
