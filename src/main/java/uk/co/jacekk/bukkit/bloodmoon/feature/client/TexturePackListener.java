package uk.co.jacekk.bukkit.bloodmoon.feature.client;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class TexturePackListener extends BaseListener<BloodMoon> {
	
	public TexturePackListener(BloodMoon plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBloodMoonStart(BloodMoonStartEvent event){
		String worldName = event.getWorld().getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.TEXTURE_PACK)){
			for (Player player : event.getWorld().getPlayers()){
				player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBloodMoonEnd(BloodMoonEndEvent event){
		String worldName = event.getWorld().getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.TEXTURE_PACK)){
			for (Player player : event.getWorld().getPlayers()){
				player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_NORMAL));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event){
		Player player = event.getPlayer();
		String fromName = event.getFrom().getName();
		String toName = player.getWorld().getName();
		PluginConfig worldConfig = plugin.getConfig(toName);
		
		if (plugin.isFeatureEnabled(toName, Feature.TEXTURE_PACK)){
			if (!plugin.isActive(fromName) && plugin.isActive(toName)){
				player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON));
			}else if (plugin.isActive(fromName) && !plugin.isActive(toName)){
				player.setTexturePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_NORMAL));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		String worldName = player.getWorld().getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.TEXTURE_PACK)){
			player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON));
		}
	}
	
}
