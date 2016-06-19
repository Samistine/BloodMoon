package uk.co.jacekk.bukkit.bloodmoon.feature.client;

import java.util.UUID;
import org.bukkit.ChatColor;
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

public class ChatMessageListener extends BaseListener<BloodMoon> {

    public ChatMessageListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBloodMoonStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isFeatureEnabled(world, Feature.CHAT_MESSAGE)) {
            String message = worldConfig.getString(Config.FEATURE_CHAT_MESSAGE_START_MESSAGE);

            if (!message.isEmpty()) {
                for (Player player : world.getPlayers()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isFeatureEnabled(world, Feature.CHAT_MESSAGE)) {
            String message = worldConfig.getString(Config.FEATURE_CHAT_MESSAGE_START_MESSAGE);

            if (!message.isEmpty() && plugin.isActive(world)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.CHAT_MESSAGE)) {
            final UUID playerUUID = player.getUniqueId();
            final String message = worldConfig.getString(Config.FEATURE_CHAT_MESSAGE_START_MESSAGE);

            if (!message.isEmpty()) {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                    @Override
                    public void run() {
                        Player player = plugin.getServer().getPlayer(playerUUID);

                        if (player != null) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }

                }, 40L);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEnd(BloodMoonEndEvent event) {
        World world = event.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isFeatureEnabled(world, Feature.CHAT_MESSAGE)) {
            String message = worldConfig.getString(Config.FEATURE_CHAT_MESSAGE_END_MESSAGE);

            if (!message.isEmpty()) {
                for (Player player : world.getPlayers()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }

}
