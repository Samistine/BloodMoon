package uk.co.jacekk.bukkit.bloodmoon.event;

import org.bukkit.block.Chest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DungeonChestFillEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Chest chest;
	
	public DungeonChestFillEvent(Chest chest){
		this.chest = chest;
	}
	
	@Override
	public HandlerList getHandlers(){
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public Chest getChest(){
		return this.chest;
	}
	
}
