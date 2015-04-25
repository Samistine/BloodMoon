/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samistine.bloodmoon.customentities;

import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EntityZombie;
import org.bukkit.entity.EntityType;

/**
 *
 * @author Samuel
 */
public enum CustomEntities {
    BloodMoonEntityZombie("Zombie", 51, EntityType.ZOMBIE, EntityZombie.class, BloodMoonEntityZombie.class);
    
    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;
    
    private CustomEntities(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getID() {
        return this.id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<? extends EntityInsentient> getNMSClass() {
        return nmsClass;
    }

    public Class<? extends EntityInsentient> getCustomClass() {
        return customClass;
    }
    
    
    
    
}
