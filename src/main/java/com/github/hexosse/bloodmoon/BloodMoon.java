package com.github.hexosse.bloodmoon;

import com.github.hexosse.bloodmoon.command.BmCommands;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.entity.BloodMoonEntityType;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.bloodmoon.exceptions.EntityRegistrationException;
import com.github.hexosse.bloodmoon.feature.client.ChatMessageListener;
import com.github.hexosse.bloodmoon.feature.client.PlaySoundListener;
import com.github.hexosse.bloodmoon.feature.client.TexturePackListener;
import com.github.hexosse.bloodmoon.feature.mob.*;
import com.github.hexosse.bloodmoon.feature.player.SwordDamageListener;
import com.github.hexosse.bloodmoon.feature.server.DisabledCommandsListener;
import com.github.hexosse.bloodmoon.feature.server.ServerCommandsListener;
import com.github.hexosse.bloodmoon.feature.spawning.*;
import com.github.hexosse.bloodmoon.feature.world.*;
import com.github.hexosse.bloodmoon.integrations.Factions;
import com.github.hexosse.bloodmoon.listeners.SpawnReasonListener;
import com.github.hexosse.bloodmoon.listeners.WorldInitListener;
import com.github.hexosse.bloodmoon.tasks.TimeMonitorTask;
import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.message.Message;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.HashMap;

import static com.github.hexosse.bloodmoon.integrations.Factions.hookFactionsPlugin;

public final class BloodMoon extends Plugin
{
	public static BloodMoon instance = null;

    public static boolean debug = false;

	public ArrayList<String> activeWorlds;
	public HashMap<String,WorldConfig> worldConfig;
    public ArrayList<String> forceWorlds;

    @Override
    public void onEnable()
    {
        /* Plugin instance */
		instance = this;

		/* */
        activeWorlds = new ArrayList<>();
        forceWorlds = new ArrayList<>();
        worldConfig = new HashMap<>();

		/* Register entities */
        try {
            BloodMoonEntityType.registerEntities();
        } catch (EntityRegistrationException e) {
            e.printStackTrace();//
        }

		/* Create a world config file per world */
        for (World world : getServer().getWorlds()) {
            createConfig(world);
        }

		/* Register commands */
		registerCommands(new BmCommands(this));

		/* Register listeners */
        getServer().getPluginManager().registerEvents(new WorldInitListener(this), this);
		getServer().getPluginManager().registerEvents(new SpawnReasonListener(this), this);
		// Mob features
		getServer().getPluginManager().registerEvents(new SuperCreepersListener(this), this);
		getServer().getPluginManager().registerEvents(new ZombieArmorListener(this), this);
		getServer().getPluginManager().registerEvents(new ZombieWeaponListener(this), this);
		getServer().getPluginManager().registerEvents(new DaylightProofMobsListener(this), this);
		getServer().getPluginManager().registerEvents(new FireArrowsListener(this), this);
		getServer().getPluginManager().registerEvents(new MaxHealthListener(this), this);
		getServer().getPluginManager().registerEvents(new MoreDropsListener(this), this);
		getServer().getPluginManager().registerEvents(new MoreExpListener(this), this);
		getServer().getPluginManager().registerEvents(new TargetDistanceListener(this), this);
		getServer().getPluginManager().registerEvents(new MovementSpeedListener(this), this);
		// World features
		getServer().getPluginManager().registerEvents(new WeatherListener(this), this);
		getServer().getPluginManager().registerEvents(new NetherSkyListener(this), this);
		getServer().getPluginManager().registerEvents(new LockInWorldListener(this), this);
		getServer().getPluginManager().registerEvents(new ExtendedNightListener(this), this);
		getServer().getPluginManager().registerEvents(new DungeonListener(this), this);
		// Spawning features
		getServer().getPluginManager().registerEvents(new SpawnOnKillListener(this), this);
		getServer().getPluginManager().registerEvents(new SpawnOnSleepListener(this), this);
		getServer().getPluginManager().registerEvents(new MoreSpawningListener(this), this);
		getServer().getPluginManager().registerEvents(new MoreMobsListener(this), this);
		getServer().getPluginManager().registerEvents(new GiantsListener(this), this);
		// Server features
		getServer().getPluginManager().registerEvents(new ServerCommandsListener(this), this);
		getServer().getPluginManager().registerEvents(new DisabledCommandsListener(this), this);
		// Player features
		getServer().getPluginManager().registerEvents(new SwordDamageListener(this), this);
		// Client Features.
		getServer().getPluginManager().registerEvents(new PlaySoundListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatMessageListener(this), this);
		getServer().getPluginManager().registerEvents(new TexturePackListener(this), this);

		/* Register tasks */
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimeMonitorTask(this), 100L, 100L);

        /* Hook faction plugin */
        hookFactionsPlugin();

		/* Console message */
		Message message = new Message();
		message.setPrefix("§4[§c" + BloodMoon.instance.getDescription().getName() + " " + BloodMoon.instance.getDescription().getVersion() + "§4]§r");
		message.add(new Message(ChatColor.YELLOW, "Enable"));
		messageManager.send(Bukkit.getConsoleSender(), message);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();

        for (World world : getServer().getWorlds())
		{
			if(isActive(world))
				deactivate(world);
		}

		/* Console message */
		Message message = new Message();
		message.setPrefix("§4[§c" + BloodMoon.instance.getDescription().getName() + " " + BloodMoon.instance.getDescription().getVersion() + "§4]§r");
		message.add(new Message(ChatColor.YELLOW, "Disabled"));
		messageManager.send(Bukkit.getConsoleSender(), message);
    }

    /**
     * Starts a bloodmoon in a specific world
     *
     * @param world The world
     */
    public void activate(World world)
	{
        Validate.notNull(world);

		if(isActive(world))
			return;

        BloodMoonStartEvent event = new BloodMoonStartEvent(world);

        getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            activeWorlds.add(world.getName());
        }
    }

	/**
	 * Stops an existing bloodmoon in a world
	 *
	 * @param world The world
	 */
	public void deactivate(World world)
	{
		Validate.notNull(world);

		if(isActive(world))
		{
			BloodMoonEndEvent event = new BloodMoonEndEvent(world);

			getServer().getPluginManager().callEvent(event);

			if(!event.isCancelled())
				activeWorlds.remove(world.getName());
		}
	}

	/**
	 * Checks if a bloodmoon is currently active in a world
	 *
	 * @param world The world
	 * @return true if a bloodmoon is active false if not
	 */
	public boolean isActive(World world)
	{
		Validate.notNull(world);
		return activeWorlds.contains(world.getName());
	}

	/**
	 * Checks if the bloodmoon is enabled for a world
	 *
	 * @param world The world
	 * @return true if the bloodmoon is enabled false if not
	 */
	public boolean isEnabled(World world)
	{
		Validate.notNull(world);

		if (!worldConfig.containsKey(world.getName()))
			return false;

		WorldConfig worldConfig = this.worldConfig.get(world.getName());

		return worldConfig.ENABLED.getValue();
	}

    /**
     * Starts a bloodmoon the next time night is reached in a specific world
     *
     * @param world The world
     */
    public void forceNextNight(World world) {
        Validate.notNull(world);
        forceWorlds.add(world.getName());
    }

    /**
     * Checks if a specific feature is enabled in a world.
     *
     * @param world The world
     * @param feature The {@link Feature} to check
     * @return true if the feature is enabled false if not
     */
    /*public Boolean isFeatureEnabled(World world, Feature feature)
    {
        Validate.notNull(world);

        if(!worldConfig.containsKey(world.getName()))
            return false;

        ConfigKey<Boolean> enabledConfigKey = feature.getEnabledConfigKey();
        WorldConfig worldConfig = this.worldConfig.get(world.getName());
		ConfigKey<Boolean> enablefeature = (ConfigKey<Boolean>)worldConfig.getKey(enabledConfigKey.getKey());
        return enablefeature.getValue();
    }*/

    /**
     * Sets up the config for a world. This should only be used by other plugins
     * if a world is being loaded that would not call a WorldInitEvent.
     *
     * @param world The {@link World} being loaded
     * @return The config object that was created for the world or null if it
     * already existed.
     */
	public WorldConfig createConfig(World world)
	{
		Validate.notNull(world);

		String worldName = world.getName();

		if(!worldConfig.containsKey(worldName))
		{
			WorldConfig worldConfig = new WorldConfig(this, this.getDataFolder(), worldName + ".yml");

			this.worldConfig.put(worldName, worldConfig);

			if(worldConfig.ALWAYS_ON.getValue())
				activate(world);

			return worldConfig;
		}

		return null;
	}

    /**
     * Gets the config for a world
     *
     * @param world The world
     * @return the {@link WorldConfig} for this world
     */
    public WorldConfig getConfig(World world)
    {
        Validate.notNull(world);

        if(!worldConfig.containsKey(world.getName()))
            return createConfig(world);

        return worldConfig.get(world.getName());
    }

    /**
     * Reloads all config files.
     */
    public void reloadWorldConfig()
	{
        for (WorldConfig config : worldConfig.values()) {
            config.load();
        }
    }

    /**
     * Gets the reason that an entity spawned. Note that these reasons are reset
     * when the server restarts.
     *
     * @param entity The entity
     * @return The {@link SpawnReason}
     */
    public SpawnReason getSpawnReason(Entity entity)
	{
        for (MetadataValue value : entity.getMetadata("spawn-reason")) {
            if (value.getOwningPlugin() instanceof BloodMoon) {
                return (SpawnReason) value.value();
            }
        }

        return SpawnReason.DEFAULT;
    }

    /**
     * @param type Entity to spawn
     * @param location Location of spawn
     * @return true if an entity can spawn at location
     */
    public boolean spawnEntityAllowed(EntityType type, Location location)
    {
        if(Factions.getPlugin()!=null && worldConfig.get(location.getWorld().getName()).INTEGRATION_FACTIONS_PREVENT_SPAWING.getValue()) {
            if(!Factions.spawnEntityAllowed(type, location))
                return false;
        }
        return true;
    }

    public String formatMessage(String message) {
        return this.formatMessage(message, true, false);
    }

    public String formatMessage(String message, boolean colour) {
        return this.formatMessage(message, colour, !colour);
    }

    public String formatMessage(String message, boolean colour, boolean version) {
        StringBuilder line = new StringBuilder();
        if(colour) {
            line.append(ChatColor.BLUE);
        }

        line.append("[");
        line.append(this.getDescription().getName());
        if(version) {
            line.append(" v");
            line.append(this.getDescription().getVersion());
        }

        line.append("] ");
        line.append(ChatColor.RESET);
        line.append(message);
        return line.toString();
    }
}
