package com.github.hexosse.bloodmoon.feature.mob;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.nms.EntitySkeleton;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;

public class FireArrowsListener extends PluginListener<BloodMoon>
{
    public FireArrowsListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        World world = projectile.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_FIRE_ARROWS_ENABLED.getValue() && worldConfig.FEATURE_FIRE_ARROWS_IGNITE_TARGET.getValue()) {
            LivingEntity shooter = (LivingEntity) projectile.getShooter();
            if (shooter.getType() == EntityType.SKELETON) { //Not sure why this is here, or why fire arrows even work with it here
                return;
            }

            if (((CraftEntity) shooter).getHandle() instanceof EntitySkeleton && projectile.getFireTicks() > 0) {
                //if (shooter != null && ((CraftEntity)shooter).getHandle() instanceof EntitySkeleton && projectile.getFireTicks() > 0){
                Block block = projectile.getWorld().getBlockAt(projectile.getLocation());

                if (block.getType() == Material.AIR) {
                    block.setType(Material.FIRE);
                }
            }
        }
    }
}
