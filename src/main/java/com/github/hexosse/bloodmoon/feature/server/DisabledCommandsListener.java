package com.github.hexosse.bloodmoon.feature.server;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class DisabledCommandsListener extends PluginListener<BloodMoon>
{
    public DisabledCommandsListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        World world = player.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        int spaceIndex = message.indexOf(' ');

        String command = (spaceIndex > 0) ? message.substring(1, spaceIndex) : message.substring(1);

        if (plugin.isActive(world) && worldConfig.FEATURE_DISABLED_COMMANDS_ENABLED.getValue()) {
            if (plugin.getConfig(world).FEATURE_DISABLED_COMMANDS_COMMANDS.getValue().contains(command)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "The /" + command + " is disabled during a bloodmoon!");
            }
        }
    }
}
