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

    private final String specifyWorld;
    private final String invalidArgsl;
    private final String noPerm;
    private final String noPermStart;
    private final String noPermStop;
    private final String notEnabledWorld;

    public BloodMoonExecuter(BloodMoon plugin) {
        super(plugin);
        this.specifyWorld = plugin.formatMessage(ChatColor.RED + "You must specify a world when using the command from the console");
        this.invalidArgsl = plugin.formatMessage(ChatColor.RED + "Invalid argument length");
        this.noPerm = plugin.formatMessage(ChatColor.RED + "You do not have permission use this command");
        this.noPermStart = plugin.formatMessage(ChatColor.RED + "You do not have permission to start a bloodmoon");
        this.noPermStop = plugin.formatMessage(ChatColor.RED + "You do not have permission to stop a bloodmoon");
        this.notEnabledWorld = plugin.formatMessage(ChatColor.RED + "The blood moon is not enabled for this world");
    }

    @CommandHandler(names = {"bloodmoon", "bm"}, description = "Toggles the bloodmoon for the current world.", usage = "[action] [world_name]")
    @CommandTabCompletion({"start|next|stop|reload"})
    public void bloodmoon(CommandSender sender, String label, String[] args) {
        sender.sendMessage(plugin.formatMessage(ChatColor.RED + "Usage: /" + label + " [action] [world_name]"));
        sender.sendMessage(ChatColor.RED + "Actions:");

        if (sender.hasPermission(Permission.ADMIN_START)) {
            sender.sendMessage(ChatColor.RED + "  start - Forces a bloodmoon to start");
            sender.sendMessage(ChatColor.RED + "  next - Forces a bloodmoon to start at the next night");
        }

        if (sender.hasPermission(Permission.ADMIN_STOP)) {
            sender.sendMessage(ChatColor.RED + "  stop - Stops a bloodmoon");
        }

        if (sender.hasPermission(Permission.ADMIN_RELOAD)) {
            sender.sendMessage(ChatColor.RED + "  reload - Reloads the config.");
        }
    }

    private List<String> activeWorldNames() {
        ArrayList<String> names = new ArrayList<>(plugin.getServer().getWorlds().size());
        for (World world : plugin.getServer().getWorlds()) {
            if (plugin.isEnabled(world)) {
                names.add(world.getName());
            }
        }
        return names;
    }

    @SubCommandHandler(parent = "bloodmoon", name = "start")
    @CommandTabCompletion({"[activeWorldNames]"})
    public void bloodmoonStart(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission(Permission.ADMIN_START)) {
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
                        sender.sendMessage(invalidArgsl);
                        return;
                }
            } else {
                switch (args.length) {
                    case 0:
                        sender.sendMessage(specifyWorld);
                        return;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalidArgsl);
                        return;
                }
            }
            World world = plugin.getServer().getWorld(worldName);
            if (world != null) {
                if (plugin.isEnabled(world)) {
                    plugin.activate(world);
                    sender.sendMessage(ChatColor.GREEN + "Bloodmoon started in " + world.getName());
                } else {
                    sender.sendMessage(notEnabledWorld);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid World");
            }
        } else {
            sender.sendMessage(noPermStart);
        }
    }

    @SubCommandHandler(parent = "bloodmoon", name = "next")
    @CommandTabCompletion({"[activeWorldNames]"})
    public void bloodmoonNext(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission(Permission.ADMIN_START)) {
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
                        sender.sendMessage(invalidArgsl);
                        return;
                }
            } else {
                switch (args.length) {
                    case 0:
                        sender.sendMessage(specifyWorld);
                        return;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalidArgsl);
                        return;
                }
            }
            World world = plugin.getServer().getWorld(worldName);
            if (world != null) {
                if (plugin.isEnabled(world)) {
                    plugin.forceNextNight(world);
                    sender.sendMessage(ChatColor.GREEN + "Bloodmoon forced in " + world.getName());
                } else {
                    sender.sendMessage(notEnabledWorld);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid World");
            }
        } else {
            sender.sendMessage(noPermStart);
        }
    }

    @SubCommandHandler(parent = "bloodmoon", name = "stop")
    @CommandTabCompletion({"[activeWorldNames]"})
    public void bloodmoonStop(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission(Permission.ADMIN_STOP)) {
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
                        sender.sendMessage(invalidArgsl);
                        return;
                }
            } else {
                switch (args.length) {
                    case 0:
                        sender.sendMessage(specifyWorld);
                        return;
                    case 1:
                        worldName = args[0];
                        break;
                    default:
                        sender.sendMessage(invalidArgsl);
                        return;
                }
            }
            World world = plugin.getServer().getWorld(worldName);
            if (world != null) {
                if (plugin.isEnabled(world)) {
                    plugin.deactivate(world);
                    sender.sendMessage(ChatColor.GREEN + "Bloodmoon stopped in " + world.getName());
                } else {
                    sender.sendMessage(notEnabledWorld);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid World");
            }
        } else {
            sender.sendMessage(noPermStop);
        }
    }

    @SubCommandHandler(parent = "bloodmoon", name = "reload")
    public void bloodmoonReload(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission(Permission.ADMIN_RELOAD)) {
            plugin.reloadWorldConfig();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded for all worlds.");
        } else {
            sender.sendMessage(noPerm);
        }
    }

}
