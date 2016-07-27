package com.github.hexosse.bloodmoon.entity;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EntityMonster;
import org.bukkit.World;

public class BloodMoonEntitySpider extends BloodMoonEntityMonster {

    public BloodMoonEntitySpider(BloodMoon plugin, EntityMonster nmsEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, type);
    }

    @Override
    public void onTick() {
        World world = getBukkitWorld();
        String entityName = getEntityType().name().toUpperCase();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (nmsEntity.getGoalTarget() instanceof EntityHuman && plugin.isActive(world) && worldConfig.FEATURE_BREAK_BLOCKS_ENABLED.getValue() && worldConfig.FEATURE_BREAK_BLOCKS_MOBS.getValue().contains(entityName) && nmsEntity.world.getTime() % 20 == 0 && nmsEntity.world.worldData.getName().equals(nmsEntity.getGoalTarget().world.worldData.getName())) {
            this.attemptBreakBlock(worldConfig, this.getBreakableTargetBlock());
        }
    }

}
