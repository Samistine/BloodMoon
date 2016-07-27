package com.github.hexosse.bloodmoon.nms;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityBlaze;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityType;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftBlaze;
import org.bukkit.plugin.Plugin;

public class EntityBlaze extends net.minecraft.server.v1_10_R1.EntityBlaze {

    private BloodMoon plugin;
    private BloodMoonEntityBlaze bloodMoonEntity;

    public EntityBlaze(World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftBlaze((CraftServer) this.plugin.getServer(), this);
        this.bloodMoonEntity = new BloodMoonEntityBlaze(this.plugin, this, BloodMoonEntityType.BLAZE);
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
