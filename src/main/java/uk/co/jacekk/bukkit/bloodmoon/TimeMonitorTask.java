package uk.co.jacekk.bukkit.bloodmoon;

import java.util.Random;
import org.bukkit.World;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;

public class TimeMonitorTask implements Runnable {

    private final Random random;
    private final BloodMoon plugin;

    public TimeMonitorTask(BloodMoon plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (World world : plugin.getServer().getWorlds()) {
            String worldName = world.getName();

            if (plugin.isEnabled(world)) {
                PluginConfig worldConfig = plugin.getConfig(world);

                if (!worldConfig.getBoolean(Config.ALWAYS_ON)) {
                    long worldTime = world.getTime();

                    if (worldTime >= 13000 && worldTime <= 13100) {
                        if (plugin.forceWorlds.contains(worldName) || this.random.nextInt(100) < worldConfig.getInt(Config.CHANCE)) {
                            if (!plugin.isActive(world)) {
                                plugin.activate(world);
                            }

                            plugin.forceWorlds.remove(worldName);
                        }
                    } else if (worldTime >= 23000 && worldTime <= 23100) {
                        if (plugin.isActive(world)) {
                            plugin.deactivate(world);
                        }
                    }
                }
            }
        }
    }

}
