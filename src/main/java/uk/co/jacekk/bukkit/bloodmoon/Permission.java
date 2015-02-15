package uk.co.jacekk.bukkit.bloodmoon;

import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.permissions.PluginPermission;

public class Permission {
	
	public static final PluginPermission ADMIN_START				= new PluginPermission("bloodmoon.admin.start",					PermissionDefault.OP,	"Allows the player to manually start a bloodmoon");
	public static final PluginPermission ADMIN_STOP					= new PluginPermission("bloodmoon.admin.stop",					PermissionDefault.OP,	"Allows the player to manually stop a bloodmoon");
	public static final PluginPermission ADMIN_RELOAD				= new PluginPermission("bloodmoon.admin.reload",				PermissionDefault.OP,	"Allows the player to reload the config files");
	public static final PluginPermission ADMIN_IGNORE_WORLD_LOCK	= new PluginPermission("bloodmoon.admin.ignore-world-lock",		PermissionDefault.OP,	"Allows the player to leave the world even if the bloodmoon is active and the lock-in-world feature is enabled");
	
}