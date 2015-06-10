package uk.co.jacekk.bukkit.bloodmoon.nms;

import java.util.List;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBow;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PathfinderGoalFleeSun;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalRestrictSun;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSkeleton;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntitySkeleton;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

//public class EntitySkeleton {
public class EntitySkeleton extends net.minecraft.server.v1_8_R3.EntitySkeleton implements IRangedEntity {

    private BloodMoon plugin;
    private BloodMoonEntitySkeleton bloodMoonEntity;

    public EntitySkeleton(net.minecraft.server.v1_8_R3.World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftSkeleton((CraftServer) this.plugin.server, this);
        this.bloodMoonEntity = new BloodMoonEntitySkeleton(this.plugin, this, (CraftLivingEntity) this.bukkitEntity, BloodMoonEntityType.SKELETON);

        try {
            ReflectionUtils.getFieldValue(this.goalSelector.getClass(), "b", List.class, this.goalSelector).clear();
            ReflectionUtils.getFieldValue(this.targetSelector.getClass(), "b", List.class, this.targetSelector).clear();

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
    public void a(net.minecraft.server.v1_8_R3.EntityLiving entityLiving, float f) {

        final EntityArrow entityarrow = new EntityArrow(this.world, this, entityLiving, 1.6f, 14 - this.world.getDifficulty().a() * 4);
        int arrow_damage = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bA());//assumming bz()
        int knockback = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bA()); //assumming bz()
        entityarrow.b(f * 2.0F + this.random.nextGaussian() * 0.25D + this.world.getDifficulty().a() * 0.11F);
        if (arrow_damage > 0) {
            entityarrow.b(entityarrow.j() + arrow_damage * 0.5 + 0.5);
            //set enchantment level
        }

        if (knockback > 0) {
            entityarrow.setKnockbackStrength(knockback);
        }

        String worldName = this.world.worldData.getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (plugin.isActive(worldName) && worldConfig.getBoolean(Config.FEATURE_FIRE_ARROWS_ENABLED) && (this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_FIRE_ARROWS_CHANCE))
                || (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bA()) > 0 || this.getSkeletonType() == 1)) {
            final EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
            this.world.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                entityarrow.setOnFire(event.getDuration());
            }
        }

//        if (plugin.isActive(worldName) && worldConfig.getBoolean(Config.FEATURE_FIRE_ARROWS_ENABLED) && (this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_FIRE_ARROWS_CHANCE))) {
//            //if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bz()) > 0 || getSkeletonType() == 1) {
//            //entityarrow.setOnFire(1024);
//            //}
//        }
        final EntityShootBowEvent event2 = CraftEventFactory.callEntityShootBowEvent(this, this.bA(), entityarrow, 0.8f);
        if (event2.isCancelled()) {
            event2.getProjectile().remove();
            return;
        }
        if (event2.getProjectile() == entityarrow.getBukkitEntity()) {
            this.world.addEntity(entityarrow);
        }
        this.makeSound("random.bow", 1.0f, 1.0f / (this.bc().nextFloat() * 0.4f + 0.8f));

        //this.world.makeSound(this, "random.bow", 1.0F, 1.0F / (this.aI().nextFloat() * 0.4F + 0.8F));
        //world.makeSound(this, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        //this.world.addEntity(entityarrow);
    }
}
