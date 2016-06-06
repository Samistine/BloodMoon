package com.github.hexosse.bloodmoon.integrations;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;

/**
 * This file is part of bloodmoon
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class Factions
{
    private static com.massivecraft.factions.Factions factions = null;

    /**
     * @return factions plugin instance
     */
    public static com.massivecraft.factions.Factions hookFactionsPlugin()
    {
        if(Factions.getPlugin()!=null)
            return Factions.getPlugin();

        PluginManager pm = Bukkit.getServer().getPluginManager();
        com.massivecraft.factions.Factions factions = (com.massivecraft.factions.Factions)pm.getPlugin("Factions");
        if(factions != null && pm.isPluginEnabled(factions))
        {
            Factions.setPlugin(factions);
            return factions;
        }
        else return null;
    }

    /**
     * @param plugin The factions plugin that this object belong to.
     */
    public static void setPlugin(com.massivecraft.factions.Factions plugin)
    {
        factions = plugin;
    }

    /**
     * @return The factions plugin that this object belong to.
     */
    public static com.massivecraft.factions.Factions getPlugin()
    {
        return factions;
    }

    /**
     * @param type Entity to spawn
     * @param location Location of spawn
     * @return true if an entity can spawn at location
     */
    public static boolean spawnEntityAllowed(EntityType type, Location location)
    {
        if(factions==null)
            return true;

        // ... get the spawn location ...
         if (location == null) return true;
        PS ps = PS.valueOf(location);

        // ... get the faction there ...
        Faction faction = BoardColl.get().getFactionAt(ps);
        if (faction == null) return true;

        // ... and if this type can't spawn in the faction ...
        if (canSpawn(faction, type)) return true;

        return false;
    }

    /**
     * @return true if an entity can spawn in the faction.
     */
    protected static boolean canSpawn(Faction faction, EntityType type)
    {
        if (MConf.get().entityTypesMonsters.contains(type))
        {
            // Monster
            return faction.getFlag(MFlag.getFlagMonsters());
        }
        else if (MConf.get().entityTypesAnimals.contains(type))
        {
            // Animal
            return faction.getFlag(MFlag.getFlagAnimals());
        }
        else
        {
            // Other
            return true;
        }
    }}
