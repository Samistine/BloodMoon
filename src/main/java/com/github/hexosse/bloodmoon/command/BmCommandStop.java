package com.github.hexosse.bloodmoon.command;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.Permissions;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandArgument;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.command.type.ArgTypeWorld;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.MessageTarget;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class BmCommandStop extends PluginCommand<BloodMoon>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public BmCommandStop(BloodMoon plugin)
	{
		super("stop", plugin);
		this.addArgument(new CommandArgument<World>("world", ArgTypeWorld.get(), true, true, "World to stop the bloodmoon."));
		this.setPermission(Permissions.ADMIN_STOP.toString());
	}

	/**
	 * Executes the given command, returning its success
	 *
	 * @param commandInfo Info about the command
	 *
	 * @return true if a valid command, otherwise false
	 */
	@Override
	public boolean onCommand(CommandInfo commandInfo)
	{
		// The world to start the bloodmoon
		World world = plugin.getServer().getWorld(commandInfo.getNamedArg("world"));

		// Prepare the message to send
		Message message = new Message();
		MessageTarget target = new MessageTarget(Bukkit.getConsoleSender()).add(commandInfo.getSender());

		if(plugin.isEnabled(world))
		{
			plugin.deactivate(world);
			message.add(new Message(ChatColor.GREEN + "Bloodmoon stopped in world " + world.getName()));
		}
		else
		{
			message.add(new Message(ChatColor.RED + "The blood moon is not enabled for the world " + world.getName()));
		}

		// Send the message
		messageManager.send(target, message);

		return true;
	}
}
