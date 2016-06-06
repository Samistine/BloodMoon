package com.github.hexosse.bloodmoon.entity;

import com.github.hexosse.bloodmoon.exceptions.EntityRegistrationException;
import com.github.hexosse.pluginframework.pluginapi.reflexion.Reflexion;
import net.minecraft.server.v1_9_R2.BiomeBase;
import net.minecraft.server.v1_9_R2.EntityInsentient;
import net.minecraft.server.v1_9_R2.EntityTypes;
import net.minecraft.server.v1_9_R2.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;
import java.util.Map;

public enum BloodMoonEntityType {

    CREEPER("Creeper", 50, EntityType.CREEPER, net.minecraft.server.v1_9_R2.EntityCreeper.class, com.github.hexosse.bloodmoon.nms.EntityCreeper.class),
	SKELETON("Skeleton", 51, EntityType.SKELETON, net.minecraft.server.v1_9_R2.EntitySkeleton.class, com.github.hexosse.bloodmoon.nms.EntitySkeleton.class),
	SPIDER("Spider", 52, EntityType.SPIDER, net.minecraft.server.v1_9_R2.EntitySpider.class, com.github.hexosse.bloodmoon.nms.EntitySpider.class),
	GIANT_ZOMBIE("Giant", 53, EntityType.GIANT, net.minecraft.server.v1_9_R2.EntityGiantZombie.class, com.github.hexosse.bloodmoon.nms.EntityGiantZombie.class),
	ZOMBIE("Zombie", 54, EntityType.ZOMBIE, net.minecraft.server.v1_9_R2.EntityZombie.class, com.github.hexosse.bloodmoon.nms.EntityZombie.class),
	GHAST("Ghast", 56, EntityType.GHAST, net.minecraft.server.v1_9_R2.EntityGhast.class, com.github.hexosse.bloodmoon.nms.EntityGhast.class),
	ENDERMAN("Enderman", 58, EntityType.ENDERMAN, net.minecraft.server.v1_9_R2.EntityEnderman.class, com.github.hexosse.bloodmoon.nms.EntityEnderman.class),
	BLAZE("Blaze", 61, EntityType.BLAZE, net.minecraft.server.v1_9_R2.EntityBlaze.class, com.github.hexosse.bloodmoon.nms.EntityBlaze.class),
	WITHER("WitherBoss", 64, EntityType.WITHER, net.minecraft.server.v1_9_R2.EntityWither.class, com.github.hexosse.bloodmoon.nms.EntityWither.class),
	WITCH("Witch", 66, EntityType.WITCH, net.minecraft.server.v1_9_R2.EntityWitch.class, com.github.hexosse.bloodmoon.nms.EntityWitch.class);

    private static boolean registered = false;
    private final String name;
    private final int id;
    private final EntityType entityType;
    private final Class<? extends EntityInsentient> nmsClass;
    private final Class<? extends EntityInsentient> bloodMoonClass;

    private BloodMoonEntityType(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> bloodMoonClass)
    {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.bloodMoonClass = bloodMoonClass;
    }

    @SuppressWarnings("unchecked")
    public static void registerEntities() throws EntityRegistrationException
	{
		if(registered)
		{
			return;
			//throw new EntityRegistrationException("Already registered.");
		}

		Map<String,Class<?>> nameMap;
		Map<Integer,Class<?>> idMap;

		try
		{
			nameMap = Reflexion.getField(EntityTypes.class, "c", Map.class, null);
			idMap = Reflexion.getField(EntityTypes.class, "e", Map.class, null);
		}
		catch(Exception e)
		{
			throw new EntityRegistrationException("Failed to get existing entity maps.", e);
		}

		for(BloodMoonEntityType entity : values())
		{
			try
			{
				nameMap.remove(entity.getName());
				idMap.remove(entity.getID());

				Reflexion.getMethod(EntityTypes.class, "a", Class.class, String.class, int.class).invoke(null, entity.getBloodMoonClass(), entity.getName(), entity.getID());
				//ReflectionUtils.invokeMethod(EntityTypes.class, "a", Void.class, null, new Class<?>[] { Class.class, String.class, int.class }, new Object[] { entity.getBloodMoonClass(), entity.getName(), entity.getID() });
			}
			catch(Exception e)
			{
				throw new EntityRegistrationException("Failed to call EntityTypes.a() for " + entity.getName(), e);
			}
		}

		for(BiomeBase biomeBase : BiomeBase.i)
		{
			if(biomeBase == null)
				break;

			for(String fieldName : new String[] { "u", "v", "w", "x" })
			{
				try
				{
					List<BiomeBase.BiomeMeta> mobList = Reflexion.getField(BiomeBase.class, fieldName, List.class, biomeBase);
					for(BiomeBase.BiomeMeta meta : mobList)
					{
						for(BloodMoonEntityType entity : values())
						{
							if(entity.getNMSClass().equals(meta.b))
								meta.b = entity.getBloodMoonClass();
						}
					}
				}
				catch(Exception e)
				{
					throw new EntityRegistrationException("Failed to modify biome data field " + fieldName, e);
				}
			}
		}

        registered = true;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public Class<? extends EntityInsentient> getNMSClass() {
        return this.nmsClass;
    }

    public Class<? extends EntityInsentient> getBloodMoonClass() {
        return this.bloodMoonClass;
    }

    private EntityInsentient createEntity(World world) {
        try {
            return this.getBloodMoonClass().getConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void spawnEntity(Location location) {
        World world = ((CraftWorld) location.getWorld()).getHandle();

        EntityInsentient entity = this.createEntity(world);
        entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(entity, SpawnReason.CUSTOM);
        entity.z(null);
    }

}
