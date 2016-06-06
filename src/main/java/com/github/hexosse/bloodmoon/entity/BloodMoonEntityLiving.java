package com.github.hexosse.bloodmoon.entity;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.pluginframework.pluginapi.reflexion.Reflexion;
import net.minecraft.server.v1_9_R2.AttributeInstance;
import net.minecraft.server.v1_9_R2.AttributeModifier;
import net.minecraft.server.v1_9_R2.EntityLiving;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Random;
import java.util.UUID;

public abstract class BloodMoonEntityLiving
{

	private static final UUID maxHealthUID = UUID.fromString("f8b0a945-2d6a-4bdb-9a6f-59c285bf1e5d");
	private static final UUID followRangeUID = UUID.fromString("1737400d-3c18-41ba-8314-49a158481e1e");
	private static final UUID knockbackResistanceUID = UUID.fromString("8742c557-fcd5-4079-a462-b58db99b0f2c");
	private static final UUID movementSpeedUID = UUID.fromString("206a89dc-ae78-4c4d-b42c-3b31db3f5a7c");
	private static final UUID attackDamageUID = UUID.fromString("7bbe3bb1-079d-4150-ac6f-669e71550776");

	protected BloodMoon plugin;
	protected EntityLiving nmsEntity;
	protected Entity bukkitEntity;
	protected BloodMoonEntityType type;

	protected Random rand = new Random();


	public BloodMoonEntityLiving(BloodMoon plugin, EntityLiving nmsEntity, BloodMoonEntityType type)
	{
		this.plugin = plugin;
		this.nmsEntity = nmsEntity;
		this.bukkitEntity = nmsEntity.getBukkitEntity();
		this.type = type;
	}

	public static BloodMoonEntityLiving getBloodMoonEntity(EntityLiving nmsEntity)
	{
		try
		{
			return Reflexion.getField(nmsEntity.getClass(), "bloodMoonEntity", BloodMoonEntityLiving.class, nmsEntity);
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException(nmsEntity.getClass().getName() + " not supported");
		}
	}

	public void setFollowRangeMultiplier(double multiplier)
	{
		AttributeInstance attributes = this.nmsEntity.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
		AttributeModifier modifier = new AttributeModifier(followRangeUID, "BloodMoon follow range multiplier", multiplier, 1);

		attributes.c(modifier);
		attributes.b(modifier);
	}

	public void clearFollowRangeMultiplier()
	{
		AttributeInstance attributes = this.nmsEntity.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
		AttributeModifier modifier = new AttributeModifier(followRangeUID, "BloodMoon follow range multiplier", 1.0d, 1);

		attributes.c(modifier);
	}

	public void setKnockbackResistanceMultiplier(double multiplier)
	{
		AttributeInstance attributes = this.nmsEntity.getAttributeInstance(GenericAttributes.c);
		//AttributeModifier modifier = new AttributeModifier(knockbackResistanceUID, "BloodMoon knockback resistance multiplier", multiplier, 1);

		//attributes.c(modifier);
		//attributes.b(modifier);
	}

	public void clearKnockbackResistanceMultiplier()
	{
		AttributeInstance attributes = this.nmsEntity.getAttributeInstance(GenericAttributes.c);
		//AttributeModifier modifier = new AttributeModifier(knockbackResistanceUID, "BloodMoon knockback resistance multiplier", , 1);
		attributes.setValue(1.0d);
		//attributes.c(modifier);
	}

	public void setSpeedMultiplier(double multiplier)
	{
		try
		{
			AttributeInstance theAttribute = this.nmsEntity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
			if(theAttribute == null)
			{
				System.err.println("That was null, wierd");
				return;
			}
			double oldValue = theAttribute.getValue();
			theAttribute.setValue(oldValue * multiplier);
			if(BloodMoon.debug)
			{
				System.err.println("Set speed attribute for mob " + this.nmsEntity.getName() + " was " + oldValue + " and is now " + theAttribute.getValue());
			}
		}
		catch(NullPointerException e)
		{
			System.err.println("Null Exception");
		}

		//AttributeInstance attributes = this.nmsEntity.getAttributeInstance(GenericAttributes.d);
		//AttributeModifier modifier = new AttributeModifier(movementSpeedUID, "BloodMoon movement speed multiplier", multiplier, 1);
		//attributes.c(modifier);
		//attributes.b(modifier);
	}

	public void clearSpeedMultiplier()
	{
		AttributeInstance attributes = this.nmsEntity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
		AttributeModifier modifier = new AttributeModifier(movementSpeedUID, "BloodMoon movement speed multiplier", 1.0d, 1);

		attributes.c(modifier);
	}

	//public abstract void onTick();
	public void onTick()
	{

	}

	public World getBukkitWorld()
	{
		return nmsEntity.getWorld().getWorld();
	}

	public String getWorldName()
	{
		return nmsEntity.getWorld().getWorld().getName();
	}

	public Location getLocation()
	{
		return bukkitEntity.getLocation();
	}

	public EntityType getEntityType()
	{
		return bukkitEntity.getType();
	}

}
