package uk.co.jacekk.bukkit.bloodmoon;

//import net.minecraft.server.v1_8_R1.IChunkLoader;
//import net.minecraft.server.v1_8_R1.WorldServer;

import org.bukkit.World;
//import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.WorldInitEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
//import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;
//import uk.co.jacekk.bukkit.bloodmoon.nms.ChunkProviderServer;

public class WorldInitListener extends BaseListener<BloodMoon> {
	
	public WorldInitListener(BloodMoon plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onWorldInit(WorldInitEvent event){
		World world = event.getWorld();
		
		plugin.createConfig(world);
		
		/*if (plugin.isFeatureEnabled(world.getName(), Feature.SPAWN_CONTROL)){
			WorldServer worldServer = ((CraftWorld) world).getHandle();
			
			try{
				IChunkLoader chunkLoader = ReflectionUtils.getFieldValue(net.minecraft.server.v1_8_R1.ChunkProviderServer.class, "chunkLoader", IChunkLoader.class, worldServer.chunkProviderServer);
				
				ChunkProviderServer newProvider = new ChunkProviderServer(this.plugin, worldServer, chunkLoader, worldServer.chunkProviderServer.chunkProvider);
				
				newProvider.chunks = worldServer.chunkProviderServer.chunks;
				newProvider.forceChunkLoad = worldServer.chunkProviderServer.forceChunkLoad;
				
				worldServer.chunkProviderServer = newProvider;
			}catch (NoSuchFieldException e){
				e.printStackTrace();
			}
		}*/
	}
	
}
