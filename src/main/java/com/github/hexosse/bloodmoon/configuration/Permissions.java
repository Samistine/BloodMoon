package com.github.hexosse.bloodmoon.configuration;

import org.bukkit.command.CommandSender;

public enum Permissions
{
    ADMIN_START("bloodmoon.admin.start")
    ,ADMIN_STOP("bloodmoon.admin.stop")
    ,ADMIN_RELOAD("bloodmoon.admin.reload")
    ,ADMIN_IGNORE_WORLD_LOCK("bloodmoon.admin.ignore-world-lock");

    private final String permission;

    // Constructeur
    Permissions(String permission)
    {
        this.permission = permission;
    }

    // Test si le sender à la permission
    public static boolean has(CommandSender sender, Permissions permission)
    {
		return has(sender, permission.permission);
    }

    // Test si le sender à la permission
    public static boolean has(CommandSender sender, String node)
    {
        return sender.hasPermission(node) || sender.hasPermission(node.toLowerCase());
    }

    // Permissions to string
    public String toString()
    {
        return permission;
    }

}
