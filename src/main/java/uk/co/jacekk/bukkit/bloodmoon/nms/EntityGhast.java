package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_9_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.CraftServer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftGhast;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGhast;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

public class EntityGhast extends net.minecraft.server.v1_9_R1.EntityGhast {

    private BloodMoon plugin;
    private BloodMoonEntityGhast bloodMoonEntity;

    public EntityGhast(World world) {
        super(world);

        Plugin gPlugin = Bukkit.getPluginManager().getPlugin("BloodMoon");

        if (gPlugin == null || !(gPlugin instanceof BloodMoon)) {
            this.world.removeEntity(this);
            return;
        }

        this.plugin = (BloodMoon) gPlugin;

        this.bukkitEntity = new CraftGhast((CraftServer) this.plugin.getServer(), this);
        this.bloodMoonEntity = new BloodMoonEntityGhast(this.plugin, this, BloodMoonEntityType.GHAST);
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
