package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_8_R3.EntityLiving;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public class BloodMoonEntityGhast extends BloodMoonEntityLiving {

    public BloodMoonEntityGhast(BloodMoon plugin, EntityLiving nmsEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, type);
    }

}
