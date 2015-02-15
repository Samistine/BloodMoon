package uk.co.jacekk.bukkit.bloodmoon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;

public class SpawnReasonListener extends BaseListener<BloodMoon> {
	
	public SpawnReasonListener(BloodMoon plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event){
		event.getEntity().setMetadata("spawn-reason", new FixedMetadataValue(plugin, event.getSpawnReason()));
	}
	
}
