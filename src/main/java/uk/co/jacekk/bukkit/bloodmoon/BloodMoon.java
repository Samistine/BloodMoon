package uk.co.jacekk.bukkit.bloodmoon;

import uk.co.jacekk.bukkit.bloodmoon.exceptions.EntityRegistrationException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang.Validate;

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

    @Override
    public void onDisable() {
        for (World world : getServer().getWorlds()) {
            if (isActive(world)) {
                deactivate(world);
            }
        }
    }

    /**
     * Starts a bloodmoon in a specific world
     *
     * @param world The world
     */
    public void activate(World world) {
        Validate.notNull(world);

        if (isActive(world)) {
            return;
        }

        BloodMoonStartEvent event = new BloodMoonStartEvent(world);

        getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            activeWorlds.add(world.getName());
        }
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
     * Stops an existing bloodmoon in a world
     *
     * @param world The world
     */
    public void deactivate(World world) {
        Validate.notNull(world);
        if (isActive(world)) {

            BloodMoonEndEvent event = new BloodMoonEndEvent(world);

            getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                activeWorlds.remove(world.getName());
            }
        }
    }

    /**
     * Checks if a bloodmoon is currently active in a world
     *
     * @param world The world
     * @return true if a bloodmoon is active false if not
     */
    public boolean isActive(World world) {
        Validate.notNull(world);
        return activeWorlds.contains(world.getName());
    }

    /**
     * Checks if the bloodmoon is enabled for a world
     *
     * @param world The world
     * @return true if the bloodmoon is enabled false if not
     */
    public boolean isEnabled(World world) {
        Validate.notNull(world);
        if (!worldConfig.containsKey(world.getName())) {
            return false;
        }

        return worldConfig.get(world.getName()).getBoolean(Config.ENABLED);
    }

    /**
     * Checks if a specific feature is enabled in a world.
     *
     * @param world The world
     * @param feature The {@link Feature} to check
     * @return true if the feature is enabled false if not
     */
    public boolean isFeatureEnabled(World world, Feature feature) {
        Validate.notNull(world);
        if (!worldConfig.containsKey(world.getName())) {
            return false;
        }

        return worldConfig.get(world.getName()).getBoolean(feature.getEnabledConfigKey());
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
        Validate.notNull(world);
        String worldName = world.getName();

        if (!worldConfig.containsKey(worldName)) {
            PluginConfig newConfig = new PluginConfig(new File(getBaseDirPath() + File.separator + worldName + ".yml"), Config.class, getLogger());

            worldConfig.put(worldName, newConfig);

            if (newConfig.getBoolean(Config.ALWAYS_ON)) {
                activate(world);
            }

            return newConfig;
        }

        return null;
    }

    /**
     * Gets the config for a world
     *
     * @param world The world
     * @return the {@link PluginConfig} for this world
     */
    public PluginConfig getConfig(World world) {
        Validate.notNull(world);
        if (!worldConfig.containsKey(world.getName())) {
            return createConfig(world);
        }

        return worldConfig.get(world.getName());
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

}
