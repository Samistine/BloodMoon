package uk.co.jacekk.bukkit.bloodmoon.nms;

import net.minecraft.server.v1_8_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftZombie;
import org.bukkit.plugin.Plugin;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityZombie;

public class EntityZombie extends net.minecraft.server.v1_8_R1.EntityZombie {
	
	private BloodMoon plugin;
	private BloodMoonEntityZombie bloodMoonEntity;
	
	public EntityZombie(World world){
		super(world);
		
		Plugin plugin = Bukkit.getPluginManager().getPlugin("BloodMoon");
		
		if (plugin == null || !(plugin instanceof BloodMoon)){
			this.world.removeEntity(this);
			return;
		}
		
		this.plugin = (BloodMoon) plugin;
		
		this.bukkitEntity = new CraftZombie((CraftServer) this.plugin.server, this);
		this.bloodMoonEntity = new BloodMoonEntityZombie(this.plugin, this, (CraftLivingEntity) this.bukkitEntity, BloodMoonEntityType.ZOMBIE);
	}
	
	@Override
	public boolean bL() {
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
