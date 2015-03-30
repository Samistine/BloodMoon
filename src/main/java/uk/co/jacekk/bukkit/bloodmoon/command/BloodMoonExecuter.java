package uk.co.jacekk.bukkit.bloodmoon.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.CommandHandler;
import uk.co.jacekk.bukkit.baseplugin.command.CommandTabCompletion;
import uk.co.jacekk.bukkit.baseplugin.command.SubCommandHandler;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Permission;

public class BloodMoonExecuter extends BaseCommandExecutor<BloodMoon> {
	
	public BloodMoonExecuter(BloodMoon plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"bloodmoon", "bm"}, description = "Toggles the bloodmoon for the current world.", usage = "[action] [world_name]")
	@CommandTabCompletion({"start|next|stop|reload"})
	public void bloodmoon(CommandSender sender, String label, String[] args){
		sender.sendMessage(plugin.formatMessage(ChatColor.RED + "Usage: /" + label + " [action] [world_name]"));
		sender.sendMessage(ChatColor.RED + "Actions:");
		
                
                if (sender.hasPermission("bloodmoon.admin.start")) {
                    	sender.sendMessage(ChatColor.RED + "  start - Forces a bloodmoon to start");
			sender.sendMessage(ChatColor.RED + "  next - Forces a bloodmoon to start at the next night");
                }
		
		if (sender.hasPermission("bloodmoon.admin.stop")){
			sender.sendMessage(ChatColor.RED + "  stop - Stops a bloodmoon");
		}
		
		if (sender.hasPermission("bloodmoon.admin.reload")){
			sender.sendMessage(ChatColor.RED + "  reload - Reloads the config.");
		}
	}
	
	private List<String> activeWorldNames(){
		ArrayList<String> names = new ArrayList<String>();
		
		for (World world : plugin.server.getWorlds()){
			String worldName = world.getName();
			
			if (plugin.isEnabled(worldName)){
				names.add(worldName);
			}
		}
		
		return names;
	}
	
	@SubCommandHandler(parent = "bloodmoon", name = "start")
	@CommandTabCompletion({"[activeWorldNames]"})
	public void bloodmoonStart(CommandSender sender, String label, String[] args){
                if (!sender.hasPermission("bloodmoon.admin.start")) {
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to start a bloodmoon"));
			return;
		}
		
		if (!(sender instanceof Player) && args.length != 1){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You must specify a world when using the command from the console"));
			return;
		}
		
		String worldName = (args.length == 1) ? args[0] : ((Player) sender).getWorld().getName();
		
		if (!plugin.isEnabled(worldName)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "The blood moon is not enabled for this world"));
			return;
		}
		
		plugin.activate(worldName);
		
		sender.sendMessage(ChatColor.GREEN + "Bloodmoon started in " + worldName);
	}
	
	@SubCommandHandler(parent = "bloodmoon", name = "next")
	@CommandTabCompletion({"[activeWorldNames]"})
	public void bloodmoonNext(CommandSender sender, String label, String[] args){
                if (!sender.hasPermission("bloodmoon.admin.start")) {
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to start a bloodmoon"));
			return;
		}
		
		if (!(sender instanceof Player) && args.length != 1){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You must specify a world when using the command from the console"));
			return;
		}
		
		String worldName = (args.length == 1) ? args[0] : ((Player) sender).getWorld().getName();
		
		if (!plugin.isEnabled(worldName)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "The blood moon is not enabled for this world"));
			return;
		}
		
		plugin.forceNextNight(worldName);
		
		sender.sendMessage(ChatColor.GREEN + "Bloodmoon forced in " + worldName);
	}
	
	@SubCommandHandler(parent = "bloodmoon", name = "stop")
	@CommandTabCompletion({"[activeWorldNames]"})
	public void bloodmoonStop(CommandSender sender, String label, String[] args){
                if (!sender.hasPermission("bloodmoon.admin.stop")) {
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission to stop a bloodmoon"));
			return;
		}
		
		if (!(sender instanceof Player) && args.length != 1){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You must specify a world when using the command from the console"));
			return;
		}
		
		String worldName = (args.length == 1) ? args[0] : ((Player) sender).getWorld().getName();
		
		if (!plugin.isEnabled(worldName)){
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "The blood moon is not enabled for this world"));
			return;
		}
		
		plugin.deactivate(worldName);
		sender.sendMessage(ChatColor.GREEN + "Bloodmoon stopped in " + worldName);
	}
	
	@SubCommandHandler(parent = "bloodmoon", name = "reload")
	public void bloodmoonReload(CommandSender sender, String label, String[] args){
                if (sender.hasPermission("bloodmoon.admin.reload")) {
			sender.sendMessage(plugin.formatMessage(ChatColor.RED + "You do not have permission use this command"));
			return;
		}
		
		plugin.reloadWorldConfig();
		sender.sendMessage(ChatColor.GREEN + "Config reloaded for all worlds.");
	}
	
}