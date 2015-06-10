package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_8_R3.EntityMonster;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;

public class BloodMoonEntityGiantZombie extends BloodMoonEntityMonster {
	
	public BloodMoonEntityGiantZombie(BloodMoon plugin, EntityMonster nmsEntity, CraftLivingEntity bukkitEntity, BloodMoonEntityType type){
		super(plugin, nmsEntity, bukkitEntity, type);
	}
	
	@Override
	public void onTick(){
		if (this.nmsEntity.locX != this.nmsEntity.lastX || this.nmsEntity.locZ != this.nmsEntity.lastZ){
			World world = this.bukkitEntity.getWorld();
			PluginConfig worldConfig = plugin.getConfig(world.getName());
			Block block = world.getBlockAt((int) this.nmsEntity.locX, (int) this.nmsEntity.locY - 1, (int) this.nmsEntity.locZ);
			
			if (worldConfig.getStringList(Config.FEATURE_GIANTS_BREAK_BLOCKS).contains(block.getType().name())){
				block.setType(Material.AIR);
				world.playSound(this.bukkitEntity.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
			}
		}
	}
	
}
