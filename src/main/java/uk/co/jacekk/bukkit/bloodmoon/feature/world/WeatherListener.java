package uk.co.jacekk.bukkit.bloodmoon.feature.world;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.weather.WeatherChangeEvent;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class WeatherListener extends BaseListener<BloodMoon> {
	
	private Random random = new Random();
	
	public WeatherListener(BloodMoon plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStart(BloodMoonStartEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (plugin.isFeatureEnabled(worldName, Feature.WEATHER) && this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_WEATHER_CHANCE)){
			world.setStorm(worldConfig.getBoolean(Config.FEATURE_WEATHER_RAIN));
			world.setThundering(worldConfig.getBoolean(Config.FEATURE_WEATHER_THUNDER));
			
			world.setWeatherDuration(10000);
			world.setThunderDuration(10000);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStop(BloodMoonEndEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		
		if (plugin.isFeatureEnabled(worldName, Feature.WEATHER)){
			world.setThundering(false);
			world.setStorm(false);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onWeatherChange(WeatherChangeEvent event){
		World world = event.getWorld();
		String worldName = world.getName();
		
		if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.WEATHER)){
			event.setCancelled(true);
		}
	}
	
}
