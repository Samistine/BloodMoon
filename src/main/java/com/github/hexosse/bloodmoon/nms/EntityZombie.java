package com.github.hexosse.bloodmoon.nms;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityType;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityZombie;
import net.minecraft.server.v1_9_R2.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftZombie;
import org.bukkit.plugin.Plugin;

public class EntityZombie extends net.minecraft.server.v1_9_R2.EntityZombie {

    private BloodMoon plugin;
    private BloodMoonEntityZombie bloodMoonEntity;

    public EntityZombie(World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftZombie((CraftServer) this.plugin.getServer(), this);
        this.bloodMoonEntity = new BloodMoonEntityZombie(this.plugin, this, BloodMoonEntityType.ZOMBIE);
    }
    
    @Override
    public boolean cp() {
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
