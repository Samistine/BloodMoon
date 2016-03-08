package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_9_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.CraftServer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftWither;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityWither;

public class EntityWither extends net.minecraft.server.v1_9_R1.EntityWither {

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
    public boolean bM() {
        try {
            this.bloodMoonEntity.onTick();
            super.bL();
        } catch (Exception e) {
            plugin.getLogger().warning("Exception caught while ticking entity");
            e.printStackTrace();
        }
        return true;
    }

}
