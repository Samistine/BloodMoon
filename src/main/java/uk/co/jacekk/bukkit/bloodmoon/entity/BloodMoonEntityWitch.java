package uk.co.jacekk.bukkit.bloodmoon.entity;

import net.minecraft.server.v1_8_R3.EntityMonster;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;

import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;

public class BloodMoonEntityWitch extends BloodMoonEntityMonster {

    public BloodMoonEntityWitch(BloodMoon plugin, EntityMonster nmsEntity, CraftLivingEntity bukkitEntity, BloodMoonEntityType type) {
        super(plugin, nmsEntity, bukkitEntity, type);
    }

    @Override
    public void onTick() {

    }

}
