package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntityMonster;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public class BloodMoonEntitySpider extends BloodMoonEntityMonster {

    public BloodMoonEntitySpider(CraftServer server, EntityLiving entity) {
        super(server, entity);
    }
	
//	public BloodMoonEntitySpider(BloodMoon plugin, EntityMonster nmsEntity, CraftLivingEntity bukkitEntity, BloodMoonEntityType type){
//		super(plugin, nmsEntity, bukkitEntity, type);
//	}
	
	@Override
	public void onTick(){
		String worldName = nmsEntity.world.worldData.getName();
		String entityName = bukkitEntity.getType().name().toUpperCase();
		PluginConfig worldConfig = plugin.getConfig(worldName);

		if (nmsEntity.getGoalTarget() instanceof EntityHuman && plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.BREAK_BLOCKS) && worldConfig.getStringList(Config.FEATURE_BREAK_BLOCKS_MOBS).contains(entityName) && nmsEntity.world.getTime() % 20 == 0 && nmsEntity.world.worldData.getName().equals(nmsEntity.getGoalTarget().world.worldData.getName())) {
			this.attemptBreakBlock(worldConfig, this.getBreakableTargetBlock());
		}
	}
	
}
