package uk.co.jacekk.bukkit.bloodmoon;

import org.bukkit.ChatColor;
import uk.co.jacekk.bukkit.bloodmoon.exceptions.EntityRegistrationException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.MetadataValue;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.bloodmoon.command.BloodMoonExecuter;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public final class BloodMoon extends BasePlugin {

    public static boolean DEBUG = false;

    private ArrayList<String> activeWorlds;
    private HashMap<String, PluginConfig> worldConfig;
    protected ArrayList<String> forceWorlds;

    @Override
    public void onEnable() {
        super.onEnable(true);

        activeWorlds = new ArrayList<>();
        forceWorlds = new ArrayList<>();
        worldConfig = new HashMap<>();

        try {
            BloodMoonEntityType.registerEntities();
        } catch (EntityRegistrationException e) {
            e.printStackTrace();//
        }

        for (World world : getServer().getWorlds()) {
            createConfig(world);
        }

        getCommandManager().registerCommandExecutor(new BloodMoonExecuter(this));
        getServer().getPluginManager().registerEvents(new WorldInitListener(this), this);
        getServer().getPluginManager().registerEvents(new SpawnReasonListener(this), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimeMonitorTask(this), 100L, 100L);

        for (Feature feature : Feature.values()) {
            try {
                Class<? extends Listener> listener = feature.getListenerClass();

                if (listener != null) {
                    getServer().getPluginManager().registerEvents(listener.getConstructor(BloodMoon.class).newInstance(this), this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts a bloodmoon in a specific world
     *
     * @param worldName The name of the world
     */
    public void activate(String worldName) {
        World world = getServer().getWorld(worldName);

        if (world == null || isActive(worldName)) {
            return;
        }

        BloodMoonStartEvent event = new BloodMoonStartEvent(world);

        getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            activeWorlds.add(worldName);
        }
    }

    /**
     * Starts a bloodmoon the next time night is reached in a specific world
     *
     * @param worldName The name of the world
     */
    public void forceNextNight(String worldName) {
        World world = getServer().getWorld(worldName);

        if (world != null) {
            forceWorlds.add(worldName);
        }
    }

    /**
     * Stops an existing bloodmoon in a world
     *
     * @param worldName The name of the world
     */
    public void deactivate(String worldName) {
        World world = getServer().getWorld(worldName);

        if (world != null || isActive(worldName)) {

            BloodMoonEndEvent event = new BloodMoonEndEvent(world);

            getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                activeWorlds.remove(worldName);
            }
        }
    }

    /**
     * Checks if a bloodmoon is currently active in a world
     *
     * @param worldName The name of the world
     * @return true if a bloodmoon is active false if not
     */
    public boolean isActive(String worldName) {
        return activeWorlds.contains(worldName);
    }

    /**
     * Checks if the bloodmoon is enabled for a world
     *
     * @param worldName The name of the world
     * @return true if the bloodmoon is enabled false if not
     */
    public boolean isEnabled(String worldName) {
        if (!worldConfig.containsKey(worldName)) {
            return false;
        }

        return worldConfig.get(worldName).getBoolean(Config.ENABLED);
    }

    /**
     * Checks if a specific feature is enabled in a world.
     *
     * @param worldName The name of the world
     * @param feature The {@link Feature} to check
     * @return true if the feature is enabled false if not
     */
    public boolean isFeatureEnabled(String worldName, Feature feature) {
        if (!worldConfig.containsKey(worldName)) {
            return false;
        }

        return worldConfig.get(worldName).getBoolean(feature.getEnabledConfigKey());
    }

    /**
     * Sets up the config for a world. This should only be used by other plugins
     * if a world is being loaded that would not call a WorldInitEvent.
     *
     * @param world The {@link World} being loaded
     * @return The config object that was created for the world or null if it
     * already existed.
     */
    public PluginConfig createConfig(World world) {
        String worldName = world.getName();

        if (!worldConfig.containsKey(worldName)) {
            PluginConfig newConfig = new PluginConfig(new File(getBaseDirPath() + File.separator + worldName + ".yml"), Config.class, log);

            worldConfig.put(worldName, newConfig);

            if (newConfig.getBoolean(Config.ALWAYS_ON)) {
                activate(worldName);
            }

            return newConfig;
        }

        return null;
    }

    /**
     * Gets the config for a world
     *
     * @param worldName The name of the world
     * @return the {@link PluginConfig} for this world
     */
    public PluginConfig getConfig(String worldName) {
        if (!worldConfig.containsKey(worldName)) {
            World world = getServer().getWorld(worldName);

            if (world != null) {
                return createConfig(world);
            }
        }

        return worldConfig.get(worldName);
    }

    /**
     * Reloads all config files.
     */
    public void reloadWorldConfig() {
        for (PluginConfig config : worldConfig.values()) {
            config.reload();
        }
    }

    /**
     * Gets the reason that an entity spawned. Note that these reasons are reset
     * when the server restarts.
     *
     * @param entity The entity
     * @return The {@link SpawnReason}
     */
    public SpawnReason getSpawnReason(Entity entity) {
        for (MetadataValue value : entity.getMetadata("spawn-reason")) {
            if (value.getOwningPlugin() instanceof BloodMoon) {
                return (SpawnReason) value.value();
            }
        }

        return SpawnReason.DEFAULT;
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
            line.append(this.description.getVersion());
        }

        line.append("] ");
        line.append(ChatColor.RESET);
        line.append(message);
        return line.toString();
    }
}
