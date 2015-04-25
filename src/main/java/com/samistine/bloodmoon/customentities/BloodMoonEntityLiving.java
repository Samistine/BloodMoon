/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samistine.bloodmoon.customentities;

import net.minecraft.server.v1_8_R2.AttributeInstance;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.World;

/**
 *
 * @author Samuel
 */
public abstract class BloodMoonEntityLiving extends EntityLiving {
    
    public BloodMoonEntityLiving(World world) {
        super(world);
    }
    
    /**
     * Get the current speed for an entity
     * @return 
     */
    public double getSpeed() {
        AttributeInstance theAttribute = this.getAttributeInstance(GenericAttributes.d);
        return theAttribute.getValue();
    }
    
    /**
     * Set the speed of the entity
     * @param speed 
     */
    public void setSpeed(double speed) {
        AttributeInstance theAttribute = this.getAttributeInstance(GenericAttributes.d);
        theAttribute.setValue(speed);
    }
    
    /**
     * Get the normal speed of the entity
     * @return
     */
    public abstract int getSpeedDefault();
    
}
