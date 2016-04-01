package com.github.hexosse.bloodmoon.command;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.Permissions;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.command.predifined.CommandHelp;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class BmCommandHelp extends CommandHelp<BloodMoon>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public BmCommandHelp(BloodMoon plugin)
	{
		super(plugin);
		this.removeArgument("page");
		this.setPermission(""+Permissions.ADMIN_START.toString()+";"+Permissions.ADMIN_STOP.toString());
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
		super.onCommand(commandInfo);

		return true;
	}
}
