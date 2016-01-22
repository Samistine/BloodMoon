package uk.co.jacekk.bukkit.bloodmoon.feature.world;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.Permission;

public class LockInWorldListener extends BaseListener<BloodMoon> {

    public LockInWorldListener(BloodMoon plugin) {
        super(plugin);
    }

    private boolean canTeleport(Player player, World from, World to) {
        return (player.hasPermission(Permission.ADMIN_IGNORE_WORLD_LOCK) || from.equals(to) || !plugin.isActive(from) || !plugin.isFeatureEnabled(from, Feature.LOCK_IN_WORLD));
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from != null && to != null && !this.canTeleport(player, from.getWorld(), to.getWorld())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot leave the world until the bloodmoon has ended.");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerPortal(PlayerPortalEvent event) {
        this.onPlayerTeleport(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World fromWorld = player.getWorld();
        World toWorld = event.getRespawnLocation().getWorld();

        if (!this.canTeleport(player, fromWorld, toWorld)) {
            event.setRespawnLocation(fromWorld.getSpawnLocation());
            player.sendMessage(ChatColor.RED + "You cannot leave the world until the bloodmoon has ended.");
        }
    }

}
