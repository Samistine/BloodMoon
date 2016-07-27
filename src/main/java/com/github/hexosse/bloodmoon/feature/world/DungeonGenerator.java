package com.github.hexosse.bloodmoon.feature.world;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.DungeonChestFillEvent;
import com.github.hexosse.bloodmoon.util.RandomUtils;
import com.github.hexosse.pluginframework.pluginapi.reflexion.Reflexion;
import net.minecraft.server.v1_10_R1.MobSpawnerAbstract;
import net.minecraft.server.v1_10_R1.TileEntityMobSpawner;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Step;
import org.bukkit.material.WoodenStep;

import java.util.Random;

public class DungeonGenerator extends BlockPopulator {

    private final BloodMoon plugin;

    public DungeonGenerator(BloodMoon plugin) {
        this.plugin = plugin;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk)
    {
        int gridX = (int) (Math.floor(chunk.getX() / 10.0d) * 10);
        int gridZ = (int) (Math.floor(chunk.getZ() / 10.0d) * 10);

        WorldConfig worldConfig = plugin.getConfig(world);

        DungeonProperties properties = new DungeonProperties(world, worldConfig, gridX, gridZ);

        if (!properties.isInChunk(chunk)) {
            return;
        }

        int worldChunkX = properties.getChunkX();
        int worldChunkZ = properties.getChunkZ();

        plugin.getLogger().info("Generated BloodMoon dungeon at " + worldChunkX + "," + worldChunkZ);

        Random rand = properties.getRandom();

        int yMax = world.getHighestBlockYAt((worldChunkX * 16) + 8, worldChunkZ * 16) - 1;
        int yMin = yMax - (properties.getLayers() * 6);

        // Walls
        for (int y = yMax + 3; y > yMin; --y) {
            for (int x = 1; x < 15; ++x) {
                for (int z = 1; z < 15; ++z) {
                    chunk.getBlock(x, y, z).setTypeId(Material.AIR.getId());
                }
            }

            for (int i = 0; i < 16; ++i) {
                chunk.getBlock(i, y, 0).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
                chunk.getBlock(i, y, 15).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);

                chunk.getBlock(0, y, i).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
                chunk.getBlock(15, y, i).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
            }
        }

        // Gate
        chunk.getBlock(7, yMax + 1, 0).setTypeId(Material.IRON_FENCE.getId());
        chunk.getBlock(8, yMax + 1, 0).setTypeId(Material.IRON_FENCE.getId());
        chunk.getBlock(7, yMax + 2, 0).setTypeId(Material.IRON_FENCE.getId());
        chunk.getBlock(8, yMax + 2, 0).setTypeId(Material.IRON_FENCE.getId());

        // Roof
        for (int i = 1; i < 15; ++i) {
            chunk.getBlock(i, yMax + 4, 1).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
            chunk.getBlock(i, yMax + 4, 14).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);

            chunk.getBlock(1, yMax + 4, i).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
            chunk.getBlock(14, yMax + 4, i).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
        }

        for (int x = 2; x < 14; ++x) {
            for (int z = 2; z < 14; ++z) {
                chunk.getBlock(x, yMax + 5, z).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
            }
        }

        WoodenStep dataWood = new WoodenStep();
        dataWood.setSpecies(TreeSpecies.REDWOOD);
        dataWood.setInverted(true);

        Step dataBrick = new Step();
        dataBrick.setMaterial(Material.SMOOTH_BRICK);
        dataBrick.setInverted(true);

        for (int layer = 0; layer <= properties.getLayers(); ++layer) {
            int yBase = yMax - (layer * 6);

            // Floors
            for (int x = 1; x < 7; ++x) {
                for (int z = 1; z < 7; ++z) {
                    chunk.getBlock(x, yBase, z).setTypeIdAndData(Material.WOOD_STEP.getId(), dataWood.getData(), false);
                    chunk.getBlock(x + 8, yBase, z).setTypeIdAndData(Material.WOOD_STEP.getId(), dataWood.getData(), false);

                    chunk.getBlock(x, yBase, z + 8).setTypeIdAndData(Material.WOOD_STEP.getId(), dataWood.getData(), false);
                    chunk.getBlock(x + 8, yBase, z + 8).setTypeIdAndData(Material.WOOD_STEP.getId(), dataWood.getData(), false);
                }
            }

            for (int x = 5; x < 11; ++x) {
                for (int z = 5; z < 11; ++z) {
                    chunk.getBlock(x, yBase, z).setTypeId(Material.AIR.getId());
                }
            }

            // Paths
            for (int i = 1; i < 5; ++i) {
                chunk.getBlock(i, yBase, 7).setTypeIdAndData(Material.STEP.getId(), dataBrick.getData(), false);
                chunk.getBlock(i, yBase, 8).setTypeIdAndData(Material.STEP.getId(), dataBrick.getData(), false);
                chunk.getBlock(i + 10, yBase, 7).setTypeIdAndData(Material.STEP.getId(), dataBrick.getData(), false);
                chunk.getBlock(i + 10, yBase, 8).setTypeIdAndData(Material.STEP.getId(), dataBrick.getData(), false);

                chunk.getBlock(7, yBase, i).setTypeIdAndData(Material.STEP.getId(), dataBrick.getData(), false);
                chunk.getBlock(8, yBase, i).setTypeIdAndData(Material.STEP.getId(), dataBrick.getData(), false);
                chunk.getBlock(7, yBase, i + 10).setTypeIdAndData(Material.STEP.getId(), dataBrick.getData(), false);
                chunk.getBlock(8, yBase, i + 10).setTypeIdAndData(Material.STEP.getId(), dataBrick.getData(), false);
            }

            // Columns
            for (int y = 0; y < 6; ++y) {
                chunk.getBlock(5, yBase + y, 5).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
                chunk.getBlock(5, yBase + y, 10).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
                chunk.getBlock(10, yBase + y, 5).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
                chunk.getBlock(10, yBase + y, 10).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
            }

            // Spawners
            Block[] spawners = new Block[]{
                chunk.getBlock(14, yBase + 1, 1),
                chunk.getBlock(1, yBase + 1, 14)
            };

            for (Block block : spawners) {
                EntityType type = EntityType.valueOf(RandomUtils.getRandom(worldConfig.FEATURE_DUNGEONS_SPAWNER_TYPES.getValue()));

                if (type != null) {
                    block.setTypeIdAndData(Material.MOB_SPAWNER.getId(), (byte) 0, false);
                    MobSpawnerAbstract spawner = ((TileEntityMobSpawner) ((CraftWorld) world).getTileEntityAt(block.getX(), block.getY(), block.getZ())).getSpawner();

                    spawner.setMobName(type.getName());

                    try {
                        Reflexion.getField(MobSpawnerAbstract.class, "minSpawnDelay").set(spawner, worldConfig.FEATURE_DUNGEONS_SPAWNER_DELAY.getValue());
                        Reflexion.getField(MobSpawnerAbstract.class, "maxSpawnDelay").set(spawner, worldConfig.FEATURE_DUNGEONS_SPAWNER_DELAY.getValue());
                        Reflexion.getField(MobSpawnerAbstract.class, "spawnCount").set(spawner, worldConfig.FEATURE_DUNGEONS_SPAWNER_COUNT.getValue());
                        Reflexion.getField(MobSpawnerAbstract.class, "maxNearbyEntities").set(spawner, worldConfig.FEATURE_DUNGEONS_SPAWNER_MAX_MOBS.getValue());
                        Reflexion.getField(MobSpawnerAbstract.class, "spawnRange").set(spawner, 4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Steps
        for (int layer = 0; layer < properties.getLayers(); ++layer) {
            int yBase = yMax - (layer * 6);

            Step topStep = new Step();
            topStep.setMaterial(Material.SMOOTH_BRICK);
            topStep.setInverted(true);

            Step bottomStep = new Step();
            bottomStep.setMaterial(Material.SMOOTH_BRICK);
            bottomStep.setInverted(false);

            if (layer % 2 == 0) {
                for (int x = 9; x < 15; ++x) {
                    chunk.getBlock(x, yBase, 14).setTypeId(Material.AIR.getId());
                }

                for (int y = 1; y < 4; ++y) {
                    int l = y * 2;

                    chunk.getBlock(7 + l, yBase - y + 1, 14).setTypeIdAndData(Material.STEP.getId(), bottomStep.getData(), false);
                    chunk.getBlock(8 + l, yBase - y, 14).setTypeIdAndData(Material.STEP.getId(), topStep.getData(), false);

                    chunk.getBlock(14, yBase - y - 2, 15 - l).setTypeIdAndData(Material.STEP.getId(), bottomStep.getData(), false);
                    chunk.getBlock(14, yBase - y - 2, 16 - l).setTypeIdAndData(Material.STEP.getId(), topStep.getData(), false);
                }
            } else {
                for (int x = 1; x < 7; ++x) {
                    chunk.getBlock(x, yBase, 1).setTypeId(Material.AIR.getId());
                }

                for (int y = 1; y < 4; ++y) {
                    int l = y * 2;

                    chunk.getBlock(8 - l, yBase - y + 1, 1).setTypeIdAndData(Material.STEP.getId(), bottomStep.getData(), false);
                    chunk.getBlock(7 - l, yBase - y, 1).setTypeIdAndData(Material.STEP.getId(), topStep.getData(), false);

                    chunk.getBlock(1, yBase - y - 2, l).setTypeIdAndData(Material.STEP.getId(), bottomStep.getData(), false);
                    chunk.getBlock(1, yBase - y - 2, l - 1).setTypeIdAndData(Material.STEP.getId(), topStep.getData(), false);
                }
            }
        }

        // Loot room
        for (int i = 0; i < 4; ++i) {
            chunk.getBlock(i + 6, yMin + 1, 5).setTypeIdAndData(Material.COBBLE_WALL.getId(), (byte) 0, false);
            chunk.getBlock(i + 6, yMin + 1, 10).setTypeIdAndData(Material.COBBLE_WALL.getId(), (byte) 0, false);

            chunk.getBlock(5, yMin + 1, i + 6).setTypeIdAndData(Material.COBBLE_WALL.getId(), (byte) 0, false);
            chunk.getBlock(10, yMin + 1, i + 6).setTypeIdAndData(Material.COBBLE_WALL.getId(), (byte) 0, false);

            for (int y = 3; y >= 0; --y) {
                chunk.getBlock(i + 6, yMin - y, 5).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
                chunk.getBlock(i + 6, yMin - y, 10).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);

                chunk.getBlock(5, yMin - y, i + 6).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
                chunk.getBlock(10, yMin - y, i + 6).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);

                for (int z = 0; z < 4; ++z) {
                    chunk.getBlock(i + 6, yMin - y, z + 6).setTypeId(Material.AIR.getId());
                }
            }
        }

        for (int x = 0; x < 6; ++x) {
            for (int z = 0; z < 6; ++z) {
                chunk.getBlock(x + 5, yMin - 4, z + 5).setTypeIdAndData(Material.SMOOTH_BRICK.getId(), (byte) rand.nextInt(3), false);
            }
        }

        chunk.getBlock(7, yMin - 3, 6).setTypeIdAndData(Material.CHEST.getId(), (byte) 2, false);
        chunk.getBlock(8, yMin - 3, 6).setTypeIdAndData(Material.CHEST.getId(), (byte) 2, false);

        chunk.getBlock(7, yMin - 3, 9).setTypeIdAndData(Material.CHEST.getId(), (byte) 3, false);
        chunk.getBlock(8, yMin - 3, 9).setTypeIdAndData(Material.CHEST.getId(), (byte) 3, false);

        chunk.getBlock(6, yMin - 3, 7).setTypeIdAndData(Material.CHEST.getId(), (byte) 4, false);
        chunk.getBlock(6, yMin - 3, 8).setTypeIdAndData(Material.CHEST.getId(), (byte) 4, false);

        chunk.getBlock(9, yMin - 3, 7).setTypeIdAndData(Material.CHEST.getId(), (byte) 5, false);
        chunk.getBlock(9, yMin - 3, 8).setTypeIdAndData(Material.CHEST.getId(), (byte) 5, false);

        // Add loot
        Chest[] chests = new Chest[]{
            (Chest) chunk.getBlock(7, yMin - 3, 6).getState(),
            (Chest) chunk.getBlock(7, yMin - 3, 9).getState(),
            (Chest) chunk.getBlock(6, yMin - 3, 7).getState(),
            (Chest) chunk.getBlock(9, yMin - 3, 7).getState()
        };

        for (Chest chest : chests) {
            Inventory inv = chest.getInventory();

            for (int i = 0; i < worldConfig.FEATURE_DUNGEONS_ITEMS_PER_CHEST.getValue(); ++i) {
                Material type = Material.getMaterial(RandomUtils.getRandom(worldConfig.FEATURE_DUNGEONS_CHEST_ITEMS.getValue()));

                if (type != null) {
                    ItemStack item = new ItemStack(type);
                    //item.setAmount(Math.min(type.getMaxStackSize(), rand.nextInt(worldConfig.getInt(WorldConfig.FEATURE_DUNGEONS_MAX_STACK_SIZE))));
                    int low = worldConfig.FEATURE_DUNGEONS_MIN_STACK_SIZE.getValue();
                    int high = worldConfig.FEATURE_DUNGEONS_MAX_STACK_SIZE.getValue();
                    Random randomSam = new Random();
                    int randomInt = randomSam.nextInt((high + 1) - low) + low;
                    item.setAmount(randomInt);

                    inv.setItem(rand.nextInt(inv.getSize()), item);
                }
            }

            this.plugin.getServer().getPluginManager().callEvent(new DungeonChestFillEvent(chest));
        }

        // Set markers for protection
        Block top = chunk.getBlock(8, yMax + 5, 8);
        Block bottom = chunk.getBlock(8, yMax - (properties.getLayers() * 6) - 4, 8);

        top.setData((byte) (top.getData() | 0x8));
        bottom.setData((byte) (bottom.getData() | 0x8));
    }

}
