package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_8_R1.*;
import org.bukkit.entity.EntityType;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;

import java.util.ArrayList;
import java.util.List;

public class ChunkProviderServer extends net.minecraft.server.v1_8_R1.ChunkProviderServer {
	
	private BloodMoon plugin;
	private List<BiomeMeta> bloodMoonMobs;
	
	public ChunkProviderServer(BloodMoon plugin, WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider){
		super(worldserver, ichunkloader, ichunkprovider);
		
		this.plugin = plugin;
		this.bloodMoonMobs = new ArrayList<BiomeMeta>();
		
		PluginConfig worldConfig = this.plugin.getConfig(this.world.worldData.getName());
		
		for (String name : worldConfig.getStringList(Config.FEATURE_SPAWN_CONTROL_SPAWN)){
			this.bloodMoonMobs.add(new BiomeMeta(EntityTypes.a(EntityType.valueOf(name).getTypeId()), 10, 4, 4)); // Entity class, weight, minGroupSize, maxGroupSize
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<BiomeMeta> getMobsFor(EnumCreatureType creatureType, int x, int y, int z){
		return (this.plugin.isActive(this.world.worldData.getName())) ? this.bloodMoonMobs : super.getMobsFor(creatureType, new BlockPosition(x, y, z));
	}
	
}
