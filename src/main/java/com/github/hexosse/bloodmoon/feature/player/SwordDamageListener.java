package com.github.hexosse.bloodmoon.feature.player;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SwordDamageListener extends PluginListener<BloodMoon>
{
    private final Random random = new Random();

    public SwordDamageListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (event.getCause() == DamageCause.ENTITY_ATTACK && plugin.isActive(world) && worldConfig.FEATURE_SWORD_DAMAGE_ENABLED.getValue()) {
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                String creatureName = creature.getType().name().toUpperCase();
                LivingEntity target = creature.getTarget();

                if (target instanceof Player) {
                    Player player = (Player) target;
                    ItemStack item = player.getItemInHand();
                    String itemName = item.getType().name().toUpperCase();

                    if (worldConfig.FEATURE_SWORD_DAMAGE_MOBS.getValue().contains(creatureName) && itemName.endsWith("_SWORD") && this.random.nextInt(100) <= worldConfig.FEATURE_SWORD_DAMAGE_CHANCE.getValue()) {
                        short damage = item.getDurability();
                        short remove = (short) (item.getType().getMaxDurability() / 50);

                        item.setDurability((short) ((damage > remove) ? damage - remove : 1));
                    }
                }
            }
        }
    }

}
