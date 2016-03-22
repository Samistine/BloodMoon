package uk.co.jacekk.bukkit.bloodmoon.feature.server;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public final class DisabledCommandsListener extends BaseListener<BloodMoon> {

    public DisabledCommandsListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        World world = player.getWorld();

        int spaceIndex = message.indexOf(' ');

        String command = (spaceIndex > 0) ? message.substring(1, spaceIndex) : message.substring(1);

        if (plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.DISABLED_COMMANDS)) {
            if (plugin.getConfig(world).getStringList(Config.FEATURE_DISABLED_COMMANDS_COMMANDS).contains(command)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "The /" + command + " is disabled during a bloodmoon!");
            }
        }
    }

}
