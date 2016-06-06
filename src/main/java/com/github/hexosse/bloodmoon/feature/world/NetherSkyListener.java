package com.github.hexosse.bloodmoon.feature.world;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import net.minecraft.server.v1_9_R1.EnumDifficulty;
import net.minecraft.server.v1_9_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_9_R1.WorldSettings;
import net.minecraft.server.v1_9_R1.WorldType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class NetherSkyListener extends PluginListener<BloodMoon>
{

    public NetherSkyListener(BloodMoon plugin) {
        super(plugin);
    }

    private void sendWorldEnvironment(Player player, Environment environment) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        CraftWorld world = (CraftWorld) player.getWorld();
        Location location = player.getLocation();

        PacketPlayOutRespawn packet = new PacketPlayOutRespawn(environment.getId(), EnumDifficulty.getById(world.getDifficulty().getValue()), WorldType.NORMAL, WorldSettings.EnumGamemode.getById(player.getGameMode().getValue()));

        craftPlayer.getHandle().playerConnection.sendPacket(packet);

        int viewDistance = plugin.getServer().getViewDistance();

        int xMin = location.getChunk().getX() - viewDistance;
        int xMax = location.getChunk().getX() + viewDistance;
        int zMin = location.getChunk().getZ() - viewDistance;
        int zMax = location.getChunk().getZ() + viewDistance;

        for (int x = xMin; x < xMax; ++x) {
            for (int z = zMin; z < zMax; ++z) {
                world.refreshChunk(x, z);
            }
        }

        //player.updateInventory(); Possibly no longer needed
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_NETHER_SKY_ENABLED.getValue()) {
            for (Player player : world.getPlayers()) {
                this.sendWorldEnvironment(player, Environment.NETHER);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        Environment environment = world.getEnvironment();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_NETHER_SKY_ENABLED.getValue()) {
            for (Player player : event.getWorld().getPlayers()) {
                this.sendWorldEnvironment(player, environment);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        World world = event.getPlayer().getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_NETHER_SKY_ENABLED.getValue()) {
            this.sendWorldEnvironment(event.getPlayer(), Environment.NETHER);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onRespawn(final PlayerRespawnEvent event) {
        World world = event.getPlayer().getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_NETHER_SKY_ENABLED.getValue()) {
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

                @Override
                public void run() {
                    sendWorldEnvironment(event.getPlayer(), Environment.NETHER);
                }

            }, 10);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onChangeWorlds(PlayerChangedWorldEvent event) {
        World world = event.getPlayer().getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_NETHER_SKY_ENABLED.getValue()) {
            this.sendWorldEnvironment(event.getPlayer(), Environment.NETHER);
        }
    }

}
