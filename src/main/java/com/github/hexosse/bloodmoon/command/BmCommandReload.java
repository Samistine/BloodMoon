package com.github.hexosse.bloodmoon.command;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.Permissions;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.command.predifined.CommandReload;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class BmCommandReload extends CommandReload<BloodMoon>
{
	/**
	 * @param plugin The plugin that this object belong to.
	 */
	public BmCommandReload(BloodMoon plugin)
	{
		super(plugin, Permissions.ADMIN_RELOAD.toString());
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
		final Player player = commandInfo.getPlayer();

		super.onCommand(commandInfo);

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				plugin.reloadWorldConfig();

				// Message
				Message message = new Message();
				message.setPrefix("§4[§b" + BloodMoon.instance.getDescription().getName() + " " + BloodMoon.instance.getDescription().getVersion() + "§4]§r");
				message.add(ChatColor.GREEN + "WorldConfig reloaded for all worlds.");
				messageManager.send(player, message);
			}

		}.runTask(plugin);

		return true;
	}
}
