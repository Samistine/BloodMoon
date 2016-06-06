package com.github.hexosse.bloodmoon.feature.client;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class TexturePackListener extends PluginListener<BloodMoon>
{
    public TexturePackListener(BloodMoon plugin)
    {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonStart(BloodMoonStartEvent event)
    {
        final World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(worldConfig.FEATURE_TEXTURE_PACK_ENABLED.getValue())
        {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
            {

                @Override
                public void run()
                {
                    sendResourcePack(world, true);
                }
            });

        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonEnd(BloodMoonEndEvent event)
    {
        final World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(worldConfig.FEATURE_TEXTURE_PACK_ENABLED.getValue())
        {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
            {

                @Override
                public void run()
                {
                    sendResourcePack(world, false);
                }
            });

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
    {
        Player player = event.getPlayer();
        World from = event.getFrom();
        World to = player.getWorld();
        WorldConfig worldConfig = plugin.getConfig(to);

        if(plugin.isActive(to) && worldConfig.FEATURE_TEXTURE_PACK_ENABLED.getValue())
        {
            if(!plugin.isActive(from) && plugin.isActive(to))
            {
                player.setResourcePack(worldConfig.FEATURE_TEXTURE_PACK_BLOODMOON.getValue());
            }
            else if(plugin.isActive(from) && !plugin.isActive(to))
            {
                player.setResourcePack(worldConfig.FEATURE_TEXTURE_PACK_NORMAL.getValue());
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        World world = player.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(plugin.isActive(world) && worldConfig.FEATURE_TEXTURE_PACK_ENABLED.getValue())
        {
            player.setResourcePack(worldConfig.FEATURE_TEXTURE_PACK_BLOODMOON.getValue());
        }
    }

    /**
     * Send the resource pack to all players in the specified world True
     *
     * @param world the world to send the texture pack to
     * @param bloodmoon true to send BloodMoon texture pack, false to send
     * normal texture pack
     */
    public void sendResourcePack(World world, boolean bloodmoon)
    {
        WorldConfig worldConfig = plugin.getConfig(world);

        String resource = bloodmoon
        ? worldConfig.FEATURE_TEXTURE_PACK_BLOODMOON.getValue()
        : worldConfig.FEATURE_TEXTURE_PACK_NORMAL.getValue();

        for(Player player : world.getPlayers())
            player.setResourcePack(resource);
    }

    /**
     * Send the resource pack to the specified player
     *
     * @param player the player to send the resource pack to
     * @param bloodmoon true to send BloodMoon texture pack, false to send
     * normal texture pack
     */
    public void sendResourcePack(Player player, boolean bloodmoon)
    {
        WorldConfig worldConfig = plugin.getConfig(player.getWorld());

        String resource = bloodmoon
        ? worldConfig.FEATURE_TEXTURE_PACK_BLOODMOON.getValue()
        : worldConfig.FEATURE_TEXTURE_PACK_NORMAL.getValue();

        player.setResourcePack(resource);
    }
}
