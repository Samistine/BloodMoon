package com.github.hexosse.bloodmoon.nms;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityGiantZombie;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityType;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftGiant;
import org.bukkit.plugin.Plugin;

public class EntityGiantZombie extends net.minecraft.server.v1_10_R1.EntityGiantZombie {

    private BloodMoon plugin;
    private BloodMoonEntityGiantZombie bloodMoonEntity;

    public EntityGiantZombie(net.minecraft.server.v1_10_R1.World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftGiant((CraftServer) this.plugin.getServer(), this);
        this.bloodMoonEntity = new BloodMoonEntityGiantZombie(this.plugin, this, BloodMoonEntityType.GIANT_ZOMBIE);
    }

    @Override
    public boolean ct() {
        try {
            this.bloodMoonEntity.onTick();
            super.co();
            motY = 10D;
        } catch (Exception e) {
            plugin.getLogger().warning("Exception caught while ticking entity");
            e.printStackTrace();
        }
        return true;
    }

}
