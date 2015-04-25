package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_8_R2.EntityLiving;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public class BloodMoonEntityGhast extends BloodMoonEntityLiving {

    public BloodMoonEntityGhast(CraftServer server, EntityLiving entity) {
        super(server, entity);
    }
	
//	public BloodMoonEntityGhast(BloodMoon plugin, EntityLiving nmsEntity, CraftLivingEntity bukkitEntity, BloodMoonEntityType type){
//		super(plugin, nmsEntity, bukkitEntity, type);
//	}
	
	@Override
	public void onTick(){
		
	}
	
}
