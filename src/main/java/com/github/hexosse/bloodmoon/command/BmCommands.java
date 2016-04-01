package com.github.hexosse.bloodmoon.command;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.google.common.collect.Lists;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class BmCommands extends PluginCommand<BloodMoon>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public BmCommands(BloodMoon plugin)
	{
		super("bloodmoon", plugin);
		this.setAliases(Lists.newArrayList("bm"));

		this.addSubCommand(new BmCommandHelp(plugin));
		this.addSubCommand(new BmCommandStart(plugin));
		this.addSubCommand(new BmCommandStop(plugin));
		this.addSubCommand(new BmCommandNext(plugin));
		this.addSubCommand(new BmCommandReload(plugin));

		/*
       this.specifyWorld = plugin.formatMessage(ChatColor.RED + "You must specify a world when using the command from the console");
        this.invalidArgs = plugin.formatMessage(ChatColor.RED + "Invalid argument length");
        this.noPerm = plugin.formatMessage(ChatColor.RED + "You do not have permission use this command");
        this.noPermStart = plugin.formatMessage(ChatColor.RED + "You do not have permission to start a bloodmoon");
        this.noPermStop = plugin.formatMessage(ChatColor.RED + "You do not have permission to stop a bloodmoon");
        this.notEnabledWorld = plugin.formatMessage(ChatColor.RED + "The blood moon is not enabled for this world");
		 */
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
		plugin.getServer().dispatchCommand(commandInfo.getSender(), "bloodmoon help");

		return true;
	}
}
