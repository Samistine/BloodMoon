package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public class FireArrowsListener extends BaseListener<BloodMoon> {
	
	public FireArrowsListener(BloodMoon plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHit(ProjectileHitEvent event){
		Entity entity = event.getEntity();
		String worldName = entity.getWorld().getName();
		PluginConfig worldConfig = plugin.getConfig(worldName);
		
		if (entity instanceof Projectile && plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.FIRE_ARROWS) && worldConfig.getBoolean(Config.FEATURE_FIRE_ARROWS_IGNITE_TARGET)){
			Projectile projectile = (Projectile) entity;
			LivingEntity shooter = (LivingEntity) projectile.getShooter();

//			if (shooter != null && ((CraftEntity) shooter).getHandle() instanceof EntitySkeleton && projectile.getFireTicks() > 0){
//				Block block = projectile.getWorld().getBlockAt(projectile.getLocation());
//
//				if (block.getType() == Material.AIR){
//					block.setType(Material.FIRE);
//				}
//			}
		}
	}
	
}
