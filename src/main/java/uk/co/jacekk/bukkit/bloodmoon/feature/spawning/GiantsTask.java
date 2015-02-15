package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import net.minecraft.server.v1_8_R1.World;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import uk.co.jacekk.bukkit.baseplugin.scheduler.BaseTask;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.nms.EntityGiantZombie;

import java.util.Random;

public class GiantsTask extends BaseTask<BloodMoon> {
	
	private CraftWorld world;
	private Random random;
	
	public GiantsTask(BloodMoon plugin, CraftWorld world){
		super(plugin);
		
		this.world = world;
		this.random = new Random();
	}
	
	@Override
	public void run(){
		long worldTime = this.world.getTime();
		
		if (worldTime < 13000 || worldTime > 23000){
			return;
		}
		
		spawn: for (Chunk chunk : this.world.getLoadedChunks()){
			if (this.random.nextInt(100) == 1){
				int x = (chunk.getX() * 16) + this.random.nextInt(12) + 2;
				int z = (chunk.getZ() * 16) + this.random.nextInt(12) + 2;
				int y = this.world.getHighestBlockYAt(x, z);
				
				Location spawnLocation = new Location(world, x, y, z);
				
				for (Entity entity : this.world.getLivingEntities()){
					if (!entity.isDead() && entity.getLocation().distanceSquared(spawnLocation) < 1024){
						continue spawn;
					}
				}
				
				World nmsWorld = this.world.getHandle();
				
				EntityGiantZombie entity = new EntityGiantZombie(nmsWorld);
				
				entity.setPositionRotation(x, y, z, 0, 90);
				nmsWorld.addEntity(entity, SpawnReason.CUSTOM);
				entity.p(null);
			}
		}
	}
	
}
