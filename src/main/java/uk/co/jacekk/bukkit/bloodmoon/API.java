/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.jacekk.bukkit.bloodmoon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.PluginManager;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

/**
 *
 * @author Samuel
 */
public class API {

    BloodMoon plugin;
    Server server;
    PluginManager pluginManager;
    ArrayList<String> activeWorlds;
    ArrayList<String> forceWorlds;
    HashMap<String, PluginConfig> worldConfig;

    API(BloodMoon plugin, ArrayList<String> activeWorlds, ArrayList<String> forceWorlds, HashMap<String, PluginConfig> worldConfig) {
        this.plugin = plugin;
        this.server = this.plugin.getServer();
        this.pluginManager = this.server.getPluginManager();
        this.activeWorlds = activeWorlds;
        this.forceWorlds = forceWorlds;
        this.worldConfig = worldConfig;
    }

    /**
     * Starts a bloodmoon in a specific world
     *
     * @param worldName The name of the world
     */
    public void activate(String worldName) {
        World world = server.getWorld(worldName);

        if (world == null || isActive(worldName)) {
            return;
        }

        BloodMoonStartEvent event = new BloodMoonStartEvent(world);

        pluginManager.callEvent(event);

        if (!event.isCancelled()) {
            activeWorlds.add(worldName);
        }
    }

    /**
     * Starts a bloodmoon the next time night is reached in a specific world
     *
     * @param worldName The name of the world
     */
    public void forceNextNight(String worldName) {
        World world = server.getWorld(worldName);

        if (world != null) {
            forceWorlds.add(worldName);
        }
    }

    /**
     * Stops an existing bloodmoon in a world
     *
     * @param worldName The name of the world
     */
    public void deactivate(String worldName) {
        World world = server.getWorld(worldName);

        if (world != null || this.isActive(worldName)) {
            BloodMoonEndEvent event = new BloodMoonEndEvent(world);
            pluginManager.callEvent(event);

            if (!event.isCancelled()) {
                this.activeWorlds.remove(worldName);
            }
        }
    }

    /**
     * Checks if a bloodmoon is currently active in a world
     *
     * @param worldName The name of the world
     * @return true if a bloodmoon is active false if not
     */
    public boolean isActive(String worldName) {
        return activeWorlds.contains(worldName);
    }


}
