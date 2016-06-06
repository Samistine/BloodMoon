package com.github.hexosse.bloodmoon.feature.world;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Random;

public class WeatherListener extends PluginListener<BloodMoon>
{

	private final Random random = new Random();

	public WeatherListener(BloodMoon plugin)
	{
		super(plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStart(BloodMoonStartEvent event)
	{
		World world = event.getWorld();
		WorldConfig worldConfig = plugin.getConfig(world);

		if(worldConfig.FEATURE_WEATHER_ENABLED.getValue() && random.nextInt(100) < worldConfig.FEATURE_WEATHER_CHANCE.getValue())
		{
			world.setStorm(worldConfig.FEATURE_WEATHER_RAIN.getValue());
			world.setThundering(worldConfig.FEATURE_WEATHER_THUNDER.getValue());

			world.setWeatherDuration(10000);
			world.setThunderDuration(10000);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onStop(BloodMoonEndEvent event)
	{
		World world = event.getWorld();
		WorldConfig worldConfig = plugin.getConfig(world);

		if(worldConfig.FEATURE_WEATHER_ENABLED.getValue())
		{
			world.setThundering(false);
			world.setStorm(false);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onWeatherChange(WeatherChangeEvent event)
	{
		World world = event.getWorld();
		WorldConfig worldConfig = plugin.getConfig(world);

		if(plugin.isActive(world) && worldConfig.FEATURE_WEATHER_ENABLED.getValue())
		{
			event.setCancelled(true);
		}
	}
}
