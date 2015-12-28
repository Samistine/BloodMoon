package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.nms.EntitySkeleton;

public class FireArrowsListener implements Listener {

    private final BloodMoon plugin;

    public FireArrowsListener(BloodMoon plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        String worldName = entity.getWorld().getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.FIRE_ARROWS) && worldConfig.getBoolean(Config.FEATURE_FIRE_ARROWS_IGNITE_TARGET)) {
            Projectile projectile = (Projectile) entity;
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
