package uk.co.jacekk.bukkit.bloodmoon.feature.mob;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.util.ListUtils;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonEndEvent;
import uk.co.jacekk.bukkit.bloodmoon.event.BloodMoonStartEvent;

public class ZombieWeaponListener implements Listener {

    private final BloodMoon plugin;
    private final Random random = new Random();

    public ZombieWeaponListener(BloodMoon plugin) {
        this.plugin = plugin;
    }

    private void giveWeapon(LivingEntity entity, PluginConfig worldConfig) {
        String name = ListUtils.getRandom(worldConfig.getStringList(Config.FEATURE_ZOMBIE_WEAPON_WEAPONS));
        Material type = Material.getMaterial(name);

        if (type == null || type.isBlock()) {
            plugin.log.warn(name + " is not a valid item name");
            return;
        }

        EntityEquipment equipment = entity.getEquipment();

        equipment.setItemInHand(new ItemStack(type));
        equipment.setItemInHandDropChance(worldConfig.getInt(Config.FEATURE_ZOMBIE_WEAPON_DROP_CHANCE) / 100.0f);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        String worldName = event.getWorld().getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (plugin.isFeatureEnabled(worldName, Feature.ZOMBIE_WEAPON)) {
            for (LivingEntity entity : event.getWorld().getLivingEntities()) {
                if (!worldConfig.getBoolean(Config.FEATURE_ZOMBIE_WEAPON_IGNORE_SPAWNERS) || plugin.getSpawnReason(entity) != SpawnReason.SPAWNER) {
                    if (entity.getType() == EntityType.ZOMBIE && this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_ZOMBIE_WEAPON_CHANCE)) {
                        this.giveWeapon(entity, worldConfig);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        String worldName = event.getLocation().getWorld().getName();
        PluginConfig worldConfig = plugin.getConfig(worldName);

        if (plugin.isActive(worldName) && plugin.isFeatureEnabled(worldName, Feature.ZOMBIE_WEAPON)) {
            LivingEntity entity = event.getEntity();

            if (!worldConfig.getBoolean(Config.FEATURE_ZOMBIE_WEAPON_IGNORE_SPAWNERS) || event.getSpawnReason() != SpawnReason.SPAWNER) {
                if (entity.getType() == EntityType.ZOMBIE && this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_ZOMBIE_WEAPON_CHANCE)) {
                    this.giveWeapon(entity, worldConfig);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        String worldName = event.getWorld().getName();

        if (plugin.isFeatureEnabled(worldName, Feature.ZOMBIE_WEAPON)) {
            for (LivingEntity entity : event.getWorld().getLivingEntities()) {
                if (entity.getType() == EntityType.ZOMBIE) {
                    EntityEquipment equipment = entity.getEquipment();

                    equipment.setItemInHand(null);
                    equipment.setItemInHandDropChance(0.0f);
                }
            }
        }
    }

}
