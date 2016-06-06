package com.github.hexosse.bloodmoon.feature.world;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.Permissions;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static com.github.hexosse.bloodmoon.configuration.Permissions.ADMIN_IGNORE_WORLD_LOCK;

public class LockInWorldListener extends PluginListener<BloodMoon>
{

    public LockInWorldListener(BloodMoon plugin) {
        super(plugin);
    }

    private boolean canTeleport(Player player, World from, World to)
    {
        WorldConfig worldConfig = plugin.getConfig(from);

        return (Permissions.has(player,ADMIN_IGNORE_WORLD_LOCK) || from.equals(to) || !plugin.isActive(from) || !worldConfig.FEATURE_LOCK_IN_WORLD_ENABLED.getValue());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from != null && to != null && !this.canTeleport(player, from.getWorld(), to.getWorld())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot leave the world until the bloodmoon has ended.");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerPortal(PlayerPortalEvent event)
    {
        this.onPlayerTeleport(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        World fromWorld = player.getWorld();
        World toWorld = event.getRespawnLocation().getWorld();

        if (!this.canTeleport(player, fromWorld, toWorld)) {
            event.setRespawnLocation(fromWorld.getSpawnLocation());
            player.sendMessage(ChatColor.RED + "You cannot leave the world until the bloodmoon has ended.");
        }
    }

}
