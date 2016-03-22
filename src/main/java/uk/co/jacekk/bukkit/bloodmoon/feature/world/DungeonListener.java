package uk.co.jacekk.bukkit.bloodmoon.feature.world;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.world.WorldInitEvent;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DungeonListener extends BaseListener<BloodMoon> {

    public DungeonListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldInit(WorldInitEvent event) {
        World world = event.getWorld();

        if (plugin.isEnabled(world) && plugin.isFeatureEnabled(world, Feature.DUNGEONS)) {
            world.getPopulators().add(new DungeonGenerator(plugin));
        }
    }

    private boolean isProtected(Location location) {
        Chunk chunk = location.getChunk();
        World world = location.getWorld();

        PluginConfig worldConfig = plugin.getConfig(world);

        int gridX = (int) (Math.floor(chunk.getX() / 10.0d) * 10);
        int gridZ = (int) (Math.floor(chunk.getZ() / 10.0d) * 10);

        DungeonProperties properties = new DungeonProperties(world, worldConfig, gridX, gridZ);

        if (properties.isInChunk(chunk)) {
            int yMin = 0;
            int yMax = 0;

            for (int y = 0; y < world.getMaxHeight(); ++y) {
                if ((chunk.getBlock(8, y, 8).getData() & (byte) 0x8) == 0x8) {
                    yMin = y;
                    break;
                }
            }

            for (int y = world.getMaxHeight(); y > 0; --y) {
                if ((chunk.getBlock(8, y, 8).getData() & (byte) 0x8) == 0x8) {
                    yMax = y;
                    break;
                }
            }

            double y = location.getY();

            return (y >= yMin && y <= yMax);
        }

        return false;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        World world = player.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        int spaceIndex = message.indexOf(' ');

        String command = (spaceIndex > 0) ? message.substring(1, spaceIndex) : message.substring(1);

        if (plugin.isFeatureEnabled(world, Feature.DISABLED_COMMANDS) && worldConfig.getStringList(Config.FEATURE_DISABLED_COMMANDS_COMMANDS).contains(command) && this.isProtected(player.getLocation())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "The /" + command + " is disabled in bloodmoon dungeons!");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        World world = block.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        List<Material> allowed = Arrays.asList(Material.IRON_FENCE, Material.MOB_SPAWNER, Material.COBBLE_WALL);

        if (plugin.isEnabled(world) && worldConfig.getBoolean(Config.FEATURE_DUNGEONS_PROTECTED) && !allowed.contains(block.getType()) && this.isProtected(block.getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();

        if (!blocks.isEmpty()) {
            World world = blocks.get(0).getWorld();
            PluginConfig worldConfig = plugin.getConfig(world);

            if (plugin.isEnabled(world) && worldConfig.getBoolean(Config.FEATURE_DUNGEONS_PROTECTED)) {
                Iterator<Block> iterator = blocks.iterator();

                while (iterator.hasNext()) {
                    Block block = iterator.next();

                    if (this.isProtected(block.getLocation())) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        World world = event.getBlock().getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isEnabled(world) && worldConfig.getBoolean(Config.FEATURE_DUNGEONS_PROTECTED)) {
            for (Block moved : event.getBlocks()) {
                if (this.isProtected(moved.getLocation())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPistonRetract(BlockPistonRetractEvent event) {
        World world = event.getBlock().getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (plugin.isEnabled(world) && worldConfig.getBoolean(Config.FEATURE_DUNGEONS_PROTECTED)) {
            if (this.isProtected(event.getRetractLocation())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        World world = block.getWorld();
        PluginConfig worldConfig = plugin.getConfig(world);

        List<Material> allowed = Arrays.asList(Material.TORCH);

        if (plugin.isEnabled(world) && worldConfig.getBoolean(Config.FEATURE_DUNGEONS_PROTECTED) && !allowed.contains(block.getType()) && this.isProtected(block.getLocation())) {
            event.setCancelled(true);
        }
    }

}
