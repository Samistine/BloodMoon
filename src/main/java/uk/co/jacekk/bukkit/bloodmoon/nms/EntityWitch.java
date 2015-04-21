package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_8_R2.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftWitch;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityWitch;

public class EntityWitch extends net.minecraft.server.v1_8_R2.EntityWitch {
	
	private BloodMoon plugin;
	private BloodMoonEntityWitch bloodMoonEntity;
	
	public EntityWitch(World world){
		super(world);
		
		Plugin plugin = Bukkit.getPluginManager().getPlugin("BloodMoon");
		
		if (plugin == null || !(plugin instanceof BloodMoon)){
			this.world.removeEntity(this);
			return;
		}
		
		this.plugin = (BloodMoon) plugin;
		
		this.bukkitEntity = new CraftWitch((CraftServer) this.plugin.server, this);
		this.bloodMoonEntity = new BloodMoonEntityWitch(this.plugin, this, (CraftLivingEntity) this.bukkitEntity, BloodMoonEntityType.WITCH);
	}
	
	@Override
	public boolean  bM() {
		try{
			this.bloodMoonEntity.onTick();
			super.bL();
		}catch (Exception e){
			plugin.log.warn("Exception caught while ticking entity");
			e.printStackTrace();
		}
		return true;
	}
	
}
