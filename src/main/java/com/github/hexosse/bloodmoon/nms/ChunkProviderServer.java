package com.github.hexosse.bloodmoon.nms;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class ChunkProviderServer extends net.minecraft.server.v1_9_R1.ChunkProviderServer {

    private final BloodMoon plugin;
    private final List<BiomeBase.BiomeMeta> bloodMoonMobs = new ArrayList<>();

    public ChunkProviderServer(BloodMoon plugin, WorldServer worldserver, IChunkLoader ichunkloader, ChunkGenerator chunkgenerator) {
        super(worldserver, ichunkloader, chunkgenerator);

        this.plugin = plugin;

        WorldConfig worldConfig = this.plugin.getConfig(this.world.worldData.world.getWorld());

        for (String name : worldConfig.FEATURE_SPAWN_CONTROL_SPAWN.getValue()) {
            Class<? extends Entity> entityClass = EntityTypes.a(EntityType.valueOf(name).getTypeId());
            this.bloodMoonMobs.add(new BiomeBase.BiomeMeta((Class<? extends EntityInsentient>) entityClass, 10, 4, 4)); // Entity class, weight, minGroupSize, maxGroupSize
        }
    }

    @SuppressWarnings("unchecked")
    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType creatureType, int x, int y, int z) {
//        return (this.plugin.isActive(this.world.worldData.getName())) ? this.bloodMoonMobs : super.getMobsFor(creatureType, new BlockPosition(x, y, z));
        return (this.plugin.isActive(this.world.worldData.world.getWorld())) ? this.bloodMoonMobs : super.chunkGenerator.getMobsFor(creatureType, new BlockPosition(x, y, z));
    }

}
