package com.github.hexosse.bloodmoon.tasks;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.pluginframework.pluginapi.PluginTask;
import org.bukkit.World;

import java.util.Random;

public class TimeMonitorTask extends PluginTask<BloodMoon>
{

    private final Random random;

    public TimeMonitorTask(BloodMoon plugin)
    {
        super(plugin);
        this.random = new Random();
    }

    @Override
    public void run()
    {
		for(World world : plugin.getServer().getWorlds())
		{
			String worldName = world.getName();

			if(plugin.isEnabled(world))
			{
				WorldConfig worldConfig = plugin.getConfig(world);

				if(!worldConfig.ALWAYS_ON.getValue())
				{
					long worldTime = world.getTime();

					if(worldTime >= 13000 && worldTime <= 13100)
					{
						if(plugin.forceWorlds.contains(worldName) || this.random.nextInt(100) < worldConfig.CHANCE.getValue())
						{
							if(!plugin.isActive(world))
								plugin.activate(world);

							plugin.forceWorlds.remove(worldName);
						}
					}
					else if(worldTime >= 23000 && worldTime <= 23100)
					{
						if(plugin.isActive(world))
							plugin.deactivate(world);
					}
				}
			}
		}
	}

}
