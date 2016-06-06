package com.github.hexosse.bloodmoon.listeners;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class SpawnReasonListener extends PluginListener<BloodMoon>
{
	public SpawnReasonListener(BloodMoon plugin)
	{
		super(plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		event.getEntity().setMetadata("spawn-reason", new FixedMetadataValue(plugin, event.getSpawnReason()));
	}
}
