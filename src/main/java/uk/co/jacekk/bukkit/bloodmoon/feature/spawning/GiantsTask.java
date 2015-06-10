package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import uk.co.jacekk.bukkit.baseplugin.scheduler.BaseTask;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.nms.EntityGiantZombie;

import java.util.Random;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class GiantsTask extends BaseTask<BloodMoon> {

    private final World world;
    private final Random random = new Random();

    public GiantsTask(BloodMoon plugin, World world) {
        super(plugin);

        this.world = world;
    }

    @Override
    public void run() {
        long worldTime = world.getTime();

        if (worldTime < 13000 || worldTime > 23000) {
            return;
        }

        spawn:
        for (Chunk chunk : world.getLoadedChunks()) {
            if (this.random.nextInt(100) == 1) {
                int x = (chunk.getX() * 16) + this.random.nextInt(12) + 2;
                int z = (chunk.getZ() * 16) + this.random.nextInt(12) + 2;
                int y = world.getHighestBlockYAt(x, z);

                Location spawnLocation = new Location(world, x, y, z);

                for (Entity entity : this.world.getLivingEntities()) {
                    if (!entity.isDead() && entity.getLocation().distanceSquared(spawnLocation) < 1024) {
                        continue spawn;
                    }
                }

                EntityGiantZombie entity = new EntityGiantZombie(world);

                entity.setPositionRotation(x, y, z, 0, 90);
                ((CraftWorld) world).getHandle().addEntity(entity, SpawnReason.CUSTOM);
                entity.p(null);
            }
        }
    }

}
