package com.github.hexosse.bloodmoon.nms;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityEndermen;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityType;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEnderman;
import org.bukkit.plugin.Plugin;

public class EntityEnderman extends net.minecraft.server.v1_10_R1.EntityEnderman {

    private BloodMoon plugin;
    private BloodMoonEntityEndermen bloodMoonEntity;

    public int bt;
    public boolean bv;

    public EntityEnderman(World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftEnderman((CraftServer) this.plugin.getServer(), this);
        this.bloodMoonEntity = new BloodMoonEntityEndermen(this.plugin, this, BloodMoonEntityType.ENDERMAN);
    }

    @Override
    public boolean ct() {
        try {
            this.bloodMoonEntity.onTick();
            super.co();
        } catch (Exception e) {
            plugin.getLogger().warning("Exception caught while ticking entity");
            e.printStackTrace();
        }
        return true;
    }

}
