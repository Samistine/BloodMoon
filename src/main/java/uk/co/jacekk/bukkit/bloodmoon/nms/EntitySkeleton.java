package uk.co.jacekk.bukkit.bloodmoon.nms;

import java.util.Set;
import net.minecraft.server.v1_9_R1.EnchantmentManager;
import net.minecraft.server.v1_9_R1.Enchantments;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EntityTippedArrow;
import net.minecraft.server.v1_9_R1.IRangedEntity;
import net.minecraft.server.v1_9_R1.MathHelper;
import net.minecraft.server.v1_9_R1.PathfinderGoalFleeSun;
import net.minecraft.server.v1_9_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_9_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_9_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_9_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_9_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_9_R1.PathfinderGoalRestrictSun;
import net.minecraft.server.v1_9_R1.SoundEffects;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R1.CraftServer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftSkeleton;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntitySkeleton;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

//public class EntitySkeleton {
public class EntitySkeleton extends net.minecraft.server.v1_9_R1.EntitySkeleton implements IRangedEntity {

    private BloodMoon plugin;
    private BloodMoonEntitySkeleton bloodMoonEntity;

    public EntitySkeleton(net.minecraft.server.v1_9_R1.World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftSkeleton((CraftServer) this.plugin.getServer(), this);
        this.bloodMoonEntity = new BloodMoonEntitySkeleton(this.plugin, this, BloodMoonEntityType.SKELETON);

        try {
            ReflectionUtils.getFieldValue(this.goalSelector.getClass(), "b", Set.class, this.goalSelector).clear();
            ReflectionUtils.getFieldValue(this.goalSelector.getClass(), "c", Set.class, this.goalSelector).clear();
            ReflectionUtils.getFieldValue(this.targetSelector.getClass(), "b", Set.class, this.targetSelector).clear();
            ReflectionUtils.getFieldValue(this.targetSelector.getClass(), "c", Set.class, this.targetSelector).clear();

            this.goalSelector.a(1, new PathfinderGoalFloat(this));
            this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
            this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0d));
            // NOTE: See bJ() below
            this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0d));
            this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
            this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));

            this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
            this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, false, true));
        } catch (Exception e) {
            e.printStackTrace();
            this.world.removeEntity(this);
        }
    }

//	@Override
//	public GroupDataEntity prepare(GroupDataEntity entityData){
//		if (this.world.worldProvider instanceof WorldProviderHell && this.aI().nextInt(5) > 0){
//			this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2d, false));
//			this.setSkeletonType(1);
//			this.setEquipment(0, new ItemStack(Items.STONE_SWORD));
//		}else{
//			this.goalSelector.a(4, new PathfinderGoalArrowAttack(this.plugin, this, 1.0d, 20, 60, 15.0f));
//
//			this.bC();
//			this.bD();
//		}
//
//		this.h(this.random.nextFloat() < 0.55F * this.world.b(this.locX, this.locY, this.locZ));
//
//		if (getEquipment(4) == null){
//			Calendar calendar = this.world.V();
//
//			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.random.nextFloat() < 0.25F){
//				setEquipment(4, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.PUMPKIN));
//				this.dropChances[4] = 0.0F;
//			}
//		}
//
//		return entityData;
//	}
//	@Override
//	public boolean bL(){
//		try{
//			this.bloodMoonEntity.onTick();
//			super.bL();
//		}catch (Exception e){
//			plugin.log.warn("Exception caught while ticking entity");
//			e.printStackTrace();
//		}
//		return true;
//	}
    @Override
    public void a(net.minecraft.server.v1_9_R1.EntityLiving entityLiving, float f) {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, this);
        double d0 = entityLiving.locX - this.locX;
        double d1 = entityLiving.getBoundingBox().b + (double) (entityLiving.length / 3.0F) - entitytippedarrow.locY;
        double d2 = entityLiving.locZ - this.locZ;
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);

        entitytippedarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float) (14 - this.world.getDifficulty().a() * 4));
        int i = EnchantmentManager.a(Enchantments.ARROW_DAMAGE, this);
        int j = EnchantmentManager.a(Enchantments.ARROW_KNOCKBACK, this);

        entitytippedarrow.c((double) (f * 2.0F) + this.random.nextGaussian() * 0.25D + (double) ((float) this.world.getDifficulty().a() * 0.11F));
        if (i > 0) {//set enchantment level
            entitytippedarrow.c(entitytippedarrow.k() + i * 0.5D + 0.5D);
        }

        if (j > 0) {//set knockback
            entitytippedarrow.setKnockbackStrength(j);
        }

        World bukkitWorld = this.world.worldData.world.getWorld();
        PluginConfig worldConfig = plugin.getConfig(bukkitWorld);

        if (plugin.isActive(bukkitWorld) && worldConfig.getBoolean(Config.FEATURE_FIRE_ARROWS_ENABLED) && (this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_FIRE_ARROWS_CHANCE))
                || (EnchantmentManager.a(Enchantments.ARROW_FIRE, this) > 0 || this.getSkeletonType() == 1)) {
            // CraftBukkit start - call EntityCombustEvent
            EntityCombustEvent event = new EntityCombustEvent(entitytippedarrow.getBukkitEntity(), 100);
            this.world.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                entitytippedarrow.setOnFire(event.getDuration());
            }
            // CraftBukkit end
        }

//        if (plugin.isActive(worldName) && worldConfig.getBoolean(Config.FEATURE_FIRE_ARROWS_ENABLED) && (this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_FIRE_ARROWS_CHANCE))) {
//            //if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bz()) > 0 || getSkeletonType() == 1) {
//            //entityarrow.setOnFire(1024);
//            //}
//        }
        // CraftBukkit start
        org.bukkit.event.entity.EntityShootBowEvent event = org.bukkit.craftbukkit.v1_9_R1.event.CraftEventFactory.callEntityShootBowEvent(this, this.getItemInMainHand(), entitytippedarrow, 0.8f);
        if (event.isCancelled()) {
            event.getProjectile().remove();
            return;
        }

        if (event.getProjectile() == entitytippedarrow.getBukkitEntity()) {
            world.addEntity(entitytippedarrow);
        }
        // CraftBukkit end

        this.a(SoundEffects.fn, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
    }
}
