package uk.co.jacekk.bukkit.bloodmoon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.MetadataValue;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.command.BloodMoonExecuter;
import uk.co.jacekk.bukkit.bloodmoon.entity.BloodMoonEntityType;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class BloodMoon extends BasePlugin {

    private ArrayList<String> activeWorlds;
    protected ArrayList<String> forceWorlds;
    protected HashMap<String, PluginConfig> worldConfig;
    public boolean debug;

    @Override
    public void onEnable() {
        super.onEnable(true);
        this.debug = false;

        initArrays();

        try {
            BloodMoonEntityType.registerEntities();
        } catch (EntityRegistrationException e) {
            e.printStackTrace();
        }

        for (World world : this.server.getWorlds()) {
            this.createConfig(world);
        }

        this.permissionManager.registerPermissions(Permission.class);
        this.commandManager.registerCommandExecutor(new BloodMoonExecuter(this));
        this.pluginManager.registerEvents(new WorldInitListener(this), this);
        this.pluginManager.registerEvents(new SpawnReasonListener(this), this);
        this.scheduler.scheduleSyncRepeatingTask(this, new TimeMonitorTask(this), 100L, 100L);

        for (Feature feature : Feature.values()) {
            try {
                Class<? extends BaseListener<BloodMoon>> listener = feature.getListenerClass();

                if (listener != null) {
                    this.pluginManager.registerEvents(listener.getConstructor(BloodMoon.class).newInstance(this), this);
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
        World world = this.server.getWorld(worldName);

        if (world == null || this.isActive(worldName)) {
            return;
        }

        BloodMoonStartEvent event = new BloodMoonStartEvent(world);

        this.pluginManager.callEvent(event);

        if (!event.isCancelled()) {
            this.activeWorlds.add(worldName);
        }
    }

    /**
     * Starts a bloodmoon the next time night is reached in a specific world
     *
     * @param worldName The name of the world
     */
    public void forceNextNight(String worldName) {
        World world = this.server.getWorld(worldName);

        if (world != null) {
            this.forceWorlds.add(worldName);
        }
    }

    /**
     * Stops an existing bloodmoon in a world
     *
     * @param worldName The name of the world
     */
    public void deactivate(String worldName) {
        World world = this.server.getWorld(worldName);

        if (world != null || this.isActive(worldName)) {
            BloodMoonEndEvent event = new BloodMoonEndEvent(world);
            this.pluginManager.callEvent(event);

            if (!event.isCancelled()) {
                this.activeWorlds.remove(worldName);
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
        return this.activeWorlds.contains(worldName);
    }

    /**
     * Checks if the bloodmoon is enabled for a world
     *
     * @param worldName The name of the world
     * @return true if the bloodmoon is enabled false if not
     */
    public boolean isEnabled(String worldName) {
        if (!this.worldConfig.containsKey(worldName)) {
            return false;
        }

        return this.worldConfig.get(worldName).getBoolean(Config.ENABLED);
    }

    /**
     * Checks if a specific feature is enabled in a world.
     *
     * @param worldName The name of the world
     * @param feature The {@link Feature} to check
     * @return true if the feature is enabled false if not
     */
    public boolean isFeatureEnabled(String worldName, Feature feature) {
        if (!this.worldConfig.containsKey(worldName)) {
            return false;
        }

        return this.worldConfig.get(worldName).getBoolean(feature.getEnabledConfigKey());
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

        if (!this.worldConfig.containsKey(worldName)) {
            PluginConfig worldConfig = new PluginConfig(new File(this.baseDirPath + File.separator + worldName + ".yml"), Config.class, this.log);

            this.worldConfig.put(worldName, worldConfig);

            if (worldConfig.getBoolean(Config.ALWAYS_ON)) {
                this.activate(worldName);
            }

            return worldConfig;
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
        if (!this.worldConfig.containsKey(worldName)) {
            World world = this.server.getWorld(worldName);

            if (world != null) {
                return this.createConfig(world);
            }
        }

        return this.worldConfig.get(worldName);
    }

    /**
     * Reloads all config files.
     */
    public void reloadWorldConfig() {
        for (PluginConfig config : this.worldConfig.values()) {
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

    private void initArrays() {
        this.activeWorlds = new ArrayList<String>();
        this.forceWorlds = new ArrayList<String>();

        this.worldConfig = new HashMap<String, PluginConfig>();
    }

}
