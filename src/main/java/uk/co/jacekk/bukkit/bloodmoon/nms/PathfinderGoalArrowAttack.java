package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.IRangedEntity;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public class PathfinderGoalArrowAttack {
//public class PathfinderGoalArrowAttack extends PathfinderGoal {

	private BloodMoon plugin;

	private final EntityInsentient a = null;
	private final IRangedEntity b = null;
	private EntityLiving c;
	
	private int d = -1;
	private double e;
	private int f;
	private int g;
	private int h;
	private float i;
	private float j;

//	public PathfinderGoalArrowAttack(BloodMoon plugin, IRangedEntity irangedentity, double f, int i, int j, float f1){
//		if (!(irangedentity instanceof EntityLiving)) {
//			throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
//		}
//
//		this.plugin = plugin;
//
//		this.b = irangedentity;
//		this.a = ((EntityInsentient) irangedentity);
//
//		this.d = -1;
//		this.e = f;
//		this.f = 0;
//        this.g = i;
//        this.h = j;
//        this.i = f1;
//        this.j = f1 * f1;
//
//		this.a(3);
//	}

//	public PathfinderGoalArrowAttack(BloodMoon plugin, IRangedEntity irangedentity, double f, int i, float f1){
//		this(plugin, irangedentity, f, i, i, f1);
//	}

//	@Override
//	public boolean a(){
//		EntityLiving entityliving = this.a.getGoalTarget();
//
//		if (entityliving == null){
//			return false;
//		}
//
//		this.c = entityliving;
//
//		return true;
//	}

//	@Override
//	public boolean b(){
//		return (this.a()) || (!this.a.getNavigation().g());
//	}

//	@Override
//	public void d(){
//		EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
//		CraftEventFactory.callEntityTargetEvent((Entity) this.b, null, reason);
//
//		this.c = null;
//		this.f = 0;
//		this.d = -1;
//	}

//	@Override
//	public void e(){
//		double d0 = this.a.e(this.c.locX, this.c.getBoundingBox().b, this.c.locZ);
//		boolean flag = this.a.getEntitySenses().canSee(this.c);
//
//		if (flag){
//			this.f += 1;
//		}else{
//			this.f = 0;
//		}
//
//		if (d0 > this.j || this.f < 20){
//			this.a.getNavigation().a(this.c, this.e);
//		}else{
//			this.a.getNavigation().g();
//		}
//
//		this.a.getControllerLook().a(this.c, 30.0F, 30.0F);
//
//		String worldName = this.a.world.worldData.getName();
//		PluginConfig worldConfig = plugin.getConfig(worldName);
//
//		this.d = Math.max(this.d - ((plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.ARROW_RATE)) ? worldConfig.getInt(Config.FEATURE_ARROW_RATE_MULTIPLIER) : 1), 0);
//
//		float f;
//
//		if (this.d == 0){
//			if (d0 > this.j || !flag){
//				return;
//			}
//
//			f = MathHelper.sqrt(d0) / this.i;
//			float f1 = f;
//
//			if (f < 0.1F){
//				f1 = 0.1F;
//			}
//
//			if (f1 > 1.0F) {
//				f1 = 1.0F;
//			}
//
//			this.b.a(this.c, f1);
//			this.d = MathHelper.d(f * (this.h - this.g) + this.g);
//		} else if (this.d < 0) {
//			f = MathHelper.sqrt(d0) / this.i;
//			this.d = MathHelper.d(f * (this.h - this.g) + this.g);
//		}
//	}
	
}
