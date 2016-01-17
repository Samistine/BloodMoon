package uk.co.jacekk.bukkit.bloodmoon.nms;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftGiant;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityGiantZombie;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;

public class EntityGiantZombie extends net.minecraft.server.v1_8_R3.EntityGiantZombie {

    private BloodMoon plugin;
    private BloodMoonEntityGiantZombie bloodMoonEntity;

    public EntityGiantZombie(net.minecraft.server.v1_8_R3.World world) {
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
    public boolean bM() {
        try {
            this.bloodMoonEntity.onTick();
            super.bL();
            motY = 10D;
        } catch (Exception e) {
            plugin.getLogger().warning("Exception caught while ticking entity");
            e.printStackTrace();
        }
        return true;
    }

}
