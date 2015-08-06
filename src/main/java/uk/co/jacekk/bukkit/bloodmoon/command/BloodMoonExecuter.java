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

public class BloodMoonExecuter extends BaseCommandExecutor<BloodMoon> {

    private final String specify_world;
    private final String invalid_args_l;
    private final String no_perm;
    private final String no_perm_start;
    private final String no_perm_stop;
    private final String not_enabled_world;

    public BloodMoonExecuter(BloodMoon plugin) {
        super(plugin);
        this.specify_world = plugin.formatMessage(ChatColor.RED + "You must specify a world when using the command from the console");
        this.invalid_args_l = plugin.formatMessage(ChatColor.RED + "Invalid argument length");
        this.no_perm = plugin.formatMessage(ChatColor.RED + "You do not have permission use this command");
        this.no_perm_start = plugin.formatMessage(ChatColor.RED + "You do not have permission to start a bloodmoon");
        this.no_perm_stop = plugin.formatMessage(ChatColor.RED + "You do not have permission to stop a bloodmoon");
        this.not_enabled_world = plugin.formatMessage(ChatColor.RED + "The blood moon is not enabled for this world");
    }

    @CommandHandler(names = {"bloodmoon", "bm"}, description = "Toggles the bloodmoon for the current world.", usage = "[action] [world_name]")
    @CommandTabCompletion({"start|next|stop|reload"})
    public void bloodmoon(CommandSender sender, String label, String[] args) {
        sender.sendMessage(plugin.formatMessage(ChatColor.RED + "Usage: /" + label + " [action] [world_name]"));
        sender.sendMessage(ChatColor.RED + "Actions:");

        if (sender.hasPermission("bloodmoon.admin.start")) {
            sender.sendMessage(ChatColor.RED + "  start - Forces a bloodmoon to start");
            sender.sendMessage(ChatColor.RED + "  next - Forces a bloodmoon to start at the next night");
        }

        if (sender.hasPermission("bloodmoon.admin.stop")) {
            sender.sendMessage(ChatColor.RED + "  stop - Stops a bloodmoon");
        }

        if (sender.hasPermission("bloodmoon.admin.reload")) {
            sender.sendMessage(ChatColor.RED + "  reload - Reloads the config.");
        }
    }

    private List<String> activeWorldNames() {
        ArrayList<String> names = new ArrayList<String>(plugin.getServer().getWorlds().size());
        for (World world : plugin.getServer().getWorlds()) {
            String worldName = world.getName();
            if (plugin.isEnabled(worldName)) {
                names.add(worldName);
            }
        }
        return names;
    }

    @SubCommandHandler(parent = "bloodmoon", name = "start")
    @CommandTabCompletion({"[activeWorldNames]"})
    public void bloodmoonStart(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("bloodmoon.admin.start")) {
            String worldName;
            if (sender instanceof Player) {
                switch (args.length) {
                    case 0:
                        worldName = ((Player) sender).getWorld().getName();
                        break;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalid_args_l);
                        return;
                }
            } else {
                switch (args.length) {
                    case 0:
                        sender.sendMessage(specify_world);
                        return;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalid_args_l);
                        return;
                }
            }
            if (plugin.isEnabled(worldName)) {
                plugin.activate(worldName);
                sender.sendMessage(ChatColor.GREEN + "Bloodmoon started in " + worldName);
            } else {
                sender.sendMessage(not_enabled_world);
            }
        } else {
            sender.sendMessage(no_perm_start);
        }
    }

    @SubCommandHandler(parent = "bloodmoon", name = "next")
    @CommandTabCompletion({"[activeWorldNames]"})
    public void bloodmoonNext(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("bloodmoon.admin.start")) {
            String worldName;
            if (sender instanceof Player) {
                switch (args.length) {
                    case 0:
                        worldName = ((Player) sender).getWorld().getName();
                        break;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalid_args_l);
                        return;
                }
            } else {
                switch (args.length) {
                    case 0:
                        sender.sendMessage(specify_world);
                        return;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalid_args_l);
                        return;
                }
            }
            if (plugin.isEnabled(worldName)) {
                plugin.forceNextNight(worldName);
                sender.sendMessage(ChatColor.GREEN + "Bloodmoon forced in " + worldName);
            } else {
                sender.sendMessage(not_enabled_world);
            }
        } else {
            sender.sendMessage(no_perm_start);
        }
    }

    @SubCommandHandler(parent = "bloodmoon", name = "stop")
    @CommandTabCompletion({"[activeWorldNames]"})
    public void bloodmoonStop(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("bloodmoon.admin.stop")) {
            String worldName;
            if (sender instanceof Player) {
                switch (args.length) {
                    case 0:
                        worldName = ((Player) sender).getWorld().getName();
                        break;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalid_args_l);
                        return;
                }
            } else {
                switch (args.length) {
                    case 0:
                        sender.sendMessage(specify_world);
                        return;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalid_args_l);
                        return;
                }
            }
            if (plugin.isEnabled(worldName)) {
                plugin.deactivate(worldName);
                sender.sendMessage(ChatColor.GREEN + "Bloodmoon stopped in " + worldName);
            } else {
                sender.sendMessage(not_enabled_world);
            }
        } else {
            sender.sendMessage(no_perm_stop);
        }
    }

    @SubCommandHandler(parent = "bloodmoon", name = "reload")
    public void bloodmoonReload(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("bloodmoon.admin.reload")) {
            plugin.reloadWorldConfig();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded for all worlds.");
        } else {
            sender.sendMessage(no_perm);
        }
    }

}
