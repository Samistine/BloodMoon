package uk.co.jacekk.bukkit.bloodmoon.feature.client;

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
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class TexturePackListener extends BaseListener<BloodMoon> {

    public TexturePackListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonStart(BloodMoonStartEvent event) {
        final World world = event.getWorld();

        if (plugin.isFeatureEnabled(world, Feature.TEXTURE_PACK)) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    sendResourcePack(world, true);
                }
            });

        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonEnd(BloodMoonEndEvent event) {
        final World world = event.getWorld();

        if (plugin.isFeatureEnabled(world, Feature.TEXTURE_PACK)) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                @Override
                public void run() {
                    sendResourcePack(world, false);
                }
            });

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World from = event.getFrom();
        World to = player.getWorld();
        PluginConfig worldConfig = plugin.getConfig(to);

        if (plugin.isFeatureEnabled(to, Feature.TEXTURE_PACK)) {
            if (!plugin.isActive(from) && plugin.isActive(to)) {
                player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON));
            } else if (plugin.isActive(from) && !plugin.isActive(to)) {
                player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_NORMAL));
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.TEXTURE_PACK)) {
            player.setResourcePack(worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON));
        }
    }

    /**
     * Send the resource pack to all players in the specified world
     *
     * @param world the world to send the texture pack to
     * @param bloodmoon true to send BloodMoon texture pack, false to send
     * normal texture pack
     */
    public void sendResourcePack(World world, boolean bloodmoon) {
        PluginConfig worldConfig = plugin.getConfig(world);
        String resource = bloodmoon
                ? worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON)
                : worldConfig.getString(Config.FEATURE_TEXTURE_PACK_NORMAL);

        for (Player player : world.getPlayers()) {
            player.setResourcePack(resource);
        }
    }

    /**
     * Send the resource pack to the specified player
     *
     * @param player the player to send the resource pack to
     * @param bloodmoon true to send BloodMoon texture pack, false to send
     * normal texture pack
     */
    public void sendResourcePack(Player player, boolean bloodmoon) {
        PluginConfig worldConfig = plugin.getConfig(player.getWorld());
        String resource = bloodmoon
                ? worldConfig.getString(Config.FEATURE_TEXTURE_PACK_BLOODMOON)
                : worldConfig.getString(Config.FEATURE_TEXTURE_PACK_NORMAL);
        player.setResourcePack(resource);
    }
}
