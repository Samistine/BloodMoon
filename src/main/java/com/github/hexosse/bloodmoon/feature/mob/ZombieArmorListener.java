package com.github.hexosse.bloodmoon.feature.mob;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.events.BloodMoonEndEvent;
import com.github.hexosse.bloodmoon.events.BloodMoonStartEvent;
import com.github.hexosse.bloodmoon.util.RandomUtils;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.logging.Level;

public class ZombieArmorListener extends PluginListener<BloodMoon>
{
    private final Random random = new Random();

    public ZombieArmorListener(BloodMoon plugin) {
        super(plugin);
    }

    private void giveArmor(LivingEntity entity, WorldConfig worldConfig) {
        String name = RandomUtils.getRandom(worldConfig.FEATURE_ZOMBIE_ARMOR_ARMOR.getValue()).toUpperCase();

        if (Material.getMaterial(name + "_BOOTS") == null) {
            plugin.getLogger().log(Level.WARNING, "{0} is not a valid armor name", name);
            return;
        }

        EntityEquipment equipment = entity.getEquipment();

        equipment.setBoots(new ItemStack(Material.getMaterial(name + "_BOOTS")));
        equipment.setLeggings(new ItemStack(Material.getMaterial(name + "_LEGGINGS")));
        equipment.setChestplate(new ItemStack(Material.getMaterial(name + "_CHESTPLATE")));
        equipment.setHelmet(new ItemStack(Material.getMaterial(name + "_HELMET")));

        float dropChance = worldConfig.FEATURE_ZOMBIE_ARMOR_DROP_CHANCE.getValue() / 100.0f;

        equipment.setBootsDropChance(dropChance);
        equipment.setLeggingsDropChance(dropChance);
        equipment.setChestplateDropChance(dropChance);
        equipment.setHelmetDropChance(dropChance);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStart(BloodMoonStartEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_ZOMBIE_ARMOR_ENABLED.getValue()) {
            for (LivingEntity entity : event.getWorld().getLivingEntities()) {
                if (!worldConfig.FEATURE_ZOMBIE_ARMOR_IGNORE_SPAWNERS.getValue() || plugin.getSpawnReason(entity) != SpawnReason.SPAWNER) {
                    if (entity.getType() == EntityType.ZOMBIE && this.random.nextInt(100) < worldConfig.FEATURE_ZOMBIE_WEAPON_CHANCE.getValue()) {
                        this.giveArmor(entity, worldConfig);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        World world = event.getLocation().getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (plugin.isActive(world) && worldConfig.FEATURE_ZOMBIE_ARMOR_ENABLED.getValue()) {
            LivingEntity entity = event.getEntity();

            if (!worldConfig.FEATURE_ZOMBIE_ARMOR_IGNORE_SPAWNERS.getValue() || event.getSpawnReason() != SpawnReason.SPAWNER) {
                if (entity.getType() == EntityType.ZOMBIE && this.random.nextInt(100) < worldConfig.FEATURE_ZOMBIE_WEAPON_CHANCE.getValue()) {
                    this.giveArmor(entity, worldConfig);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onStop(BloodMoonEndEvent event) {
        World world = event.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (worldConfig.FEATURE_ZOMBIE_ARMOR_ENABLED.getValue()) {
            for (LivingEntity entity : event.getWorld().getLivingEntities()) {
                if (entity.getType() == EntityType.ZOMBIE) {
                    EntityEquipment equipment = entity.getEquipment();

                    equipment.setBoots(null);
                    equipment.setLeggings(null);
                    equipment.setChestplate(null);
                    equipment.setHelmet(null);

                    equipment.setBootsDropChance(0.0f);
                    equipment.setLeggingsDropChance(0.0f);
                    equipment.setChestplateDropChance(0.0f);
                    equipment.setHelmetDropChance(0.0f);
                }
            }
        }
    }
}
