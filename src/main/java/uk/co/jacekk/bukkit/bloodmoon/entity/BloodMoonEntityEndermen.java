package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EntityMonster;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public class BloodMoonEntityEndermen extends BloodMoonEntityMonster {

    public BloodMoonEntityEndermen(BloodMoon plugin, EntityMonster nmsEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, type);
    }

    @Override
    public void onTick() {
        World world = getBukkitWorld();
        String entityName = getEntityType().name().toUpperCase();
        PluginConfig worldConfig = plugin.getConfig(world);

        if (nmsEntity.getGoalTarget() instanceof EntityHuman && plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.BREAK_BLOCKS) && worldConfig.getStringList(Config.FEATURE_BREAK_BLOCKS_MOBS).contains(entityName) && nmsEntity.world.getTime() % 20 == 0 && nmsEntity.world.worldData.getName().equals(nmsEntity.getGoalTarget().world.worldData.getName())) {
            Block[] blocks = new Block[3];

            blocks[0] = this.getBreakableTargetBlock();
            blocks[1] = blocks[0].getRelative(BlockFace.DOWN);
            blocks[2] = blocks[1].getRelative(BlockFace.DOWN);

            for (Block block : blocks) {
                this.attemptBreakBlock(worldConfig, block);
            }
        }
    }

}
