package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.EntityType;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;

import java.util.ArrayList;
import java.util.List;

public class ChunkProviderServer extends net.minecraft.server.v1_8_R3.ChunkProviderServer {

    private final BloodMoon plugin;
    private final List<BiomeBase.BiomeMeta> bloodMoonMobs;

    public ChunkProviderServer(BloodMoon plugin, WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
        super(worldserver, ichunkloader, ichunkprovider);

        this.plugin = plugin;
        this.bloodMoonMobs = new ArrayList<BiomeBase.BiomeMeta>();

        PluginConfig worldConfig = this.plugin.getConfig(this.world.worldData.getName());

        for (String name : worldConfig.getStringList(Config.FEATURE_SPAWN_CONTROL_SPAWN)) {
            Class<? extends Entity> entityClass = EntityTypes.a(EntityType.valueOf(name).getTypeId());
            this.bloodMoonMobs.add(new BiomeBase.BiomeMeta((Class<? extends EntityInsentient>) entityClass, 10, 4, 4)); // Entity class, weight, minGroupSize, maxGroupSize
        }
    }

    @SuppressWarnings("unchecked")
    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType creatureType, int x, int y, int z) {
        return (this.plugin.isActive(this.world.worldData.getName())) ? this.bloodMoonMobs : super.getMobsFor(creatureType, new BlockPosition(x, y, z));
    }

}
