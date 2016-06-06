package com.github.hexosse.bloodmoon.nms;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntitySpider;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityType;
import net.minecraft.server.v1_9_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.CraftServer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftSpider;
import org.bukkit.plugin.Plugin;

public class EntitySpider extends net.minecraft.server.v1_9_R1.EntitySpider {

    private BloodMoon plugin;
    private BloodMoonEntitySpider bloodMoonEntity;

    public EntitySpider(World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftSpider((CraftServer) this.plugin.getServer(), this);
        this.bloodMoonEntity = new BloodMoonEntitySpider(this.plugin, this, BloodMoonEntityType.SPIDER);
    }

    @Override
    public boolean co() {
        try {
            this.bloodMoonEntity.onTick();
            super.cn();
        } catch (Exception e) {
            plugin.getLogger().warning("Exception caught while ticking entity");
            e.printStackTrace();
        }
        return true;
    }

}
