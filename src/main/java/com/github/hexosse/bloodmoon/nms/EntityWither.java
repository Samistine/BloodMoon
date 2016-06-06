package com.github.hexosse.bloodmoon.nms;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityType;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityWither;
import net.minecraft.server.v1_9_R2.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftWither;
import org.bukkit.plugin.Plugin;

public class EntityWither extends net.minecraft.server.v1_9_R2.EntityWither {

    private BloodMoon plugin;
    private BloodMoonEntityWither bloodMoonEntity;

    public EntityWither(World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftWither((CraftServer) this.plugin.getServer(), this);
        this.bloodMoonEntity = new BloodMoonEntityWither(this.plugin, this, BloodMoonEntityType.WITCH);
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
