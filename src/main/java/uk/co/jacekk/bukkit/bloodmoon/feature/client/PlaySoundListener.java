package uk.co.jacekk.bukkit.bloodmoon.feature.client;

import java.util.List;
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

        if (plugin.isFeatureEnabled(world, Feature.PLAY_SOUND)) {
            playSound(world, event.getWorld().getPlayers().toArray(new Player[event.getWorld().getPlayers().size()]));
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        World world = event.getPlayer().getWorld();

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.PLAY_SOUND)) {
            playSound(world, event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final World world = event.getPlayer().getWorld();

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.PLAY_SOUND)) {
            final UUID playerUUID = event.getPlayer().getUniqueId();

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    Player player = plugin.getServer().getPlayer(playerUUID);

                    if (player != null) {
                        playSound(world, player);
                    }
                }

            }, 40L);
        }
    }

    /**
     * Play the sound to the specified player
     *
     * @param world world's config to get sound from
     * @param players player/players to send sound too
     */
    private void playSound(World world, Player... players) {
        PluginConfig worldConfig = plugin.getConfig(world);
        Sound sound = Sound.valueOf(worldConfig.getString(Config.FEATURE_PLAY_SOUND_SOUND));//TODO: Add error message if this is not a valid sound
        float volume = (float) worldConfig.getDouble(Config.FEATURE_PLAY_SOUND_VOLUME);
        float pitch = (float) worldConfig.getDouble(Config.FEATURE_PLAY_SOUND_PITCH);
        for (Player player : players) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

}
