package com.github.hexosse.bloodmoon.feature.spawning;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.bloodmoon.configuration.WorldConfig;
import com.github.hexosse.bloodmoon.util.RandomUtils;
import com.github.hexosse.pluginframework.pluginapi.PluginListener;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class SpawnOnKillListener extends PluginListener<BloodMoon>
{
    private static final List<DamageCause> playerCauses = Collections.unmodifiableList(new ArrayList<DamageCause>(5) {
        {
            add(DamageCause.ENTITY_ATTACK);
            add(DamageCause.MAGIC);
            add(DamageCause.POISON);
            add(DamageCause.FIRE_TICK);
            add(DamageCause.PROJECTILE);
        }
    });

    private final Random random = new Random();

    public SpawnOnKillListener(BloodMoon plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getWorld();
        WorldConfig worldConfig = plugin.getConfig(world);

        if (entity instanceof Creature && plugin.isActive(world) && worldConfig.FEATURE_SPAWN_ON_KILL_ENABLED.getValue()) {
            Creature creature = (Creature) entity;
            EntityDamageEvent lastDamage = entity.getLastDamageCause();

            if (lastDamage != null && creature.getTarget() instanceof Player && playerCauses.contains(lastDamage.getCause()) && worldConfig.FEATURE_SPAWN_ON_KILL_MOBS.getValue().contains(creature.getType().name().toUpperCase())) {
                if (this.random.nextInt(100) < worldConfig.FEATURE_SPAWN_ON_KILL_CHANCE.getValue()) {
                    String mobName = RandomUtils.getRandom(worldConfig.FEATURE_SPAWN_ON_KILL_SPAWN.getValue());
                    EntityType creatureType = EntityType.fromName(mobName.toUpperCase());

                    if (creatureType != null && plugin.spawnEntityAllowed(creatureType, creature.getLocation())) {
                        world.spawnEntity(creature.getLocation(), creatureType);
                        //world.spawn(creature.getLocation(), creatureType.getEntityClass(), SpawnReason.NATURAL);
                    }
                }
            }
        }
    }
}
