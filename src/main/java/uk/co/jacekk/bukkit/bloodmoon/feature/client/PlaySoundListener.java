package uk.co.jacekk.bukkit.bloodmoon.feature.client;

import java.util.UUID;
import org.bukkit.Sound;
import org.bukkit.World;
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
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class PlaySoundListener extends BaseListener<BloodMoon> {

    public PlaySoundListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isFeatureEnabled(world, Feature.PLAY_SOUND)) {
            for (Player player : event.getWorld().getPlayers()) {
                player.playSound(player.getLocation(), Sound.valueOf(worldConfig.getString(Config.FEATURE_PLAY_SOUND_SOUND)), (float) worldConfig.getDouble(Config.FEATURE_PLAY_SOUND_VOLUME), (float) worldConfig.getDouble(Config.FEATURE_PLAY_SOUND_PITCH));
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.PLAY_SOUND)) {
            player.playSound(player.getLocation(), Sound.valueOf(worldConfig.getString(Config.FEATURE_PLAY_SOUND_SOUND)), (float) worldConfig.getDouble(Config.FEATURE_PLAY_SOUND_VOLUME), (float) worldConfig.getDouble(Config.FEATURE_PLAY_SOUND_PITCH));
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        final PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.PLAY_SOUND)) {
            final UUID playerUUID = player.getUniqueId();

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    Player player = plugin.getServer().getPlayer(playerUUID);

                    if (player != null) {
                        player.playSound(player.getLocation(), Sound.valueOf(worldConfig.getString(Config.FEATURE_PLAY_SOUND_SOUND)), (float) worldConfig.getDouble(Config.FEATURE_PLAY_SOUND_VOLUME), (float) worldConfig.getDouble(Config.FEATURE_PLAY_SOUND_PITCH));
                    }
                }

            }, 40L);
        }
    }

}
