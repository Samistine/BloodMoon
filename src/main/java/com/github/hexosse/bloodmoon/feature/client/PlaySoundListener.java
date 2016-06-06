package com.github.hexosse.bloodmoon.feature.client;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlaySoundListener extends PluginListener<BloodMoon>
{
    public PlaySoundListener(BloodMoon plugin)
    {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonStart(BloodMoonStartEvent event)
    {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(worldConfig.FEATURE_PLAY_SOUND_ENABLED.getValue())
        {
            for(Player player : event.getWorld().getPlayers())
            {
                player.playSound(player.getLocation(), Sound.valueOf(worldConfig.FEATURE_PLAY_SOUND_SOUND.getValue()), (float) worldConfig.FEATURE_PLAY_SOUND_VOLUME.getValue(), (float) worldConfig.FEATURE_PLAY_SOUND_PITCH.getValue());
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
    {
        Player player = event.getPlayer();
        World world = player.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if(plugin.isActive(world) && worldConfig.FEATURE_PLAY_SOUND_ENABLED.getValue())
        {
            player.playSound(player.getLocation(), Sound.valueOf(worldConfig.FEATURE_PLAY_SOUND_SOUND.getValue()), (float) worldConfig.FEATURE_PLAY_SOUND_VOLUME.getValue(), (float) worldConfig.FEATURE_PLAY_SOUND_PITCH.getValue());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        World world = player.getWorld();
        final WorldConfig worldConfig = plugin.getConfig(world);

        if(plugin.isActive(world) && worldConfig.FEATURE_PLAY_SOUND_ENABLED.getValue())
        {
            final UUID playerUUID = player.getUniqueId();

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
            {

                @Override
                public void run()
                {
                    Player player = plugin.getServer().getPlayer(playerUUID);

                    if(player != null)
                    {
                        player.playSound(player.getLocation(), Sound.valueOf(worldConfig.FEATURE_PLAY_SOUND_SOUND.getValue()), (float) worldConfig.FEATURE_PLAY_SOUND_VOLUME.getValue(), (float) worldConfig.FEATURE_PLAY_SOUND_PITCH.getValue());
                    }
                }

            }, 40L);
        }
    }
}
