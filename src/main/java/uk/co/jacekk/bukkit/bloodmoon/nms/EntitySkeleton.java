package uk.co.jacekk.bukkit.bloodmoon.nms;

import java.util.List;
import net.minecraft.server.v1_8_R1.Enchantment;
import net.minecraft.server.v1_8_R1.EnchantmentManager;
import net.minecraft.server.v1_8_R1.EntityArrow;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.IRangedEntity;
import net.minecraft.server.v1_8_R1.NBTTagList;
import net.minecraft.server.v1_8_R1.PathfinderGoalFleeSun;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R1.PathfinderGoalRestrictSun;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftSkeleton;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntitySkeleton;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

//public class EntitySkeleton {
public class EntitySkeleton extends net.minecraft.server.v1_8_R1.EntitySkeleton implements IRangedEntity {

    private BloodMoon plugin;
    private BloodMoonEntitySkeleton bloodMoonEntity;

    public EntitySkeleton(net.minecraft.server.v1_8_R1.World world) {
        super(world);

        Plugin plugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (plugin == null || !(plugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) plugin;

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
//	@Override
    public void a(net.minecraft.server.v1_8_R1.EntityLiving entityLiving, float f) {
        //EntityArrow entityarrow = new EntityArrow(this.world, this, entityLiving, 1.6F, 14 - this.world.difficulty.a() * 4);

        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bz());//assumming bz()
        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bz()); //assumming bz()

        //entityarrow.b(f * 2.0F + this.random.nextGaussian() * 0.25D + this.world.difficulty.a() * 0.11F);

        if (i > 0) {
            //entityarrow.b(entityarrow.e() + i * 0.5D + 0.5D);
            //set enchantment level
        }

        if (j > 0) {
            //entityarrow.setKnockbackStrength(j);
        }

        String worldName = this.world.worldData.getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bz()) > 0 || getSkeletonType() == 1 || (plugin.isActive(worldName) && worldConfig.getBoolean(Config.FEATURE_FIRE_ARROWS_ENABLED) && (this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_FIRE_ARROWS_CHANCE)))) {
            //entityarrow.setOnFire(1024);
        }

        //this.world.makeSound(this, "random.bow", 1.0F, 1.0F / (this.aI().nextFloat() * 0.4F + 0.8F));
        world.makeSound(this, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        //this.world.addEntity(entityarrow);
    }
}
