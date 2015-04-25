package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntityMonster;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public class BloodMoonEntityWither extends BloodMoonEntityMonster {

    public BloodMoonEntityWither(CraftServer server, EntityLiving entity) {
        super(server, entity);
    }
	
//	public BloodMoonEntityWither(BloodMoon plugin, EntityMonster nmsEntity, CraftLivingEntity bukkitEntity, BloodMoonEntityType type){
//		super(plugin, nmsEntity, bukkitEntity, type);
//	}
	
	@Override
	public void onTick(){
		
	}
	
}
