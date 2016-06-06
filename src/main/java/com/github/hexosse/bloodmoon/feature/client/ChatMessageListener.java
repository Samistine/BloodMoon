package com.github.hexosse.bloodmoon.feature.client;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class ChatMessageListener extends PluginListener<BloodMoon>
{
    public ChatMessageListener(BloodMoon plugin)
    {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonStart(BloodMoonStartEvent event)
    {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(worldConfig.FEATURE_CHAT_MESSAGE_ENABLED.getValue())
        {
            String message = worldConfig.FEATURE_CHAT_MESSAGE_START_MESSAGE.getValue();

            if(!message.isEmpty())
            {
                for(Player player : world.getPlayers())
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
    {
        Player player = event.getPlayer();
        World world = player.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(worldConfig.FEATURE_CHAT_MESSAGE_ENABLED.getValue())
        {
            String message = worldConfig.FEATURE_CHAT_MESSAGE_START_MESSAGE.getValue();

            if(!message.isEmpty())
            {
                if(plugin.isActive(world))
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        World world = player.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(plugin.isActive(world) && worldConfig.FEATURE_CHAT_MESSAGE_ENABLED.getValue())
        {
            final UUID playerUUID = player.getUniqueId();
            final String message = worldConfig.FEATURE_CHAT_MESSAGE_START_MESSAGE.getValue();

            if(!message.isEmpty())
            {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                {

                    @Override
                    public void run()
                    {
                        Player player = plugin.getServer().getPlayer(playerUUID);

                        if(player != null)
                        {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }

                }, 40L);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEnd(BloodMoonEndEvent event)
    {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(worldConfig.FEATURE_CHAT_MESSAGE_ENABLED.getValue())
        {
            String message = worldConfig.FEATURE_CHAT_MESSAGE_END_MESSAGE.getValue();

            if(!message.isEmpty())
            {
                for(Player player : world.getPlayers())
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }
}
