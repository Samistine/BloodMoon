package uk.co.jacekk.bukkit.bloodmoon.feature.spawning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.baseplugin.util.ListUtils;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
import uk.co.jacekk.bukkit.bloodmoon.Feature;

public final class SpawnOnKillListener extends BaseListener<BloodMoon> {

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
        PluginConfig worldConfig = plugin.getConfig(world);

        if (entity instanceof Creature && plugin.isActive(world) && plugin.isFeatureEnabled(world, Feature.SPAWN_ON_KILL)) {
            Creature creature = (Creature) entity;
            EntityDamageEvent lastDamage = entity.getLastDamageCause();

            if (lastDamage != null && creature.getTarget() instanceof Player && playerCauses.contains(lastDamage.getCause()) && worldConfig.getStringList(Config.FEATURE_SPAWN_ON_KILL_MOBS).contains(creature.getType().name().toUpperCase())) {
                if (this.random.nextInt(100) < worldConfig.getInt(Config.FEATURE_SPAWN_ON_KILL_CHANCE)) {
                    String mobName = ListUtils.getRandom(worldConfig.getStringList(Config.FEATURE_SPAWN_ON_KILL_SPAWN));
                    EntityType creatureType = EntityType.fromName(mobName.toUpperCase());

                    if (creatureType != null) {
                        world.spawnEntity(creature.getLocation(), creatureType);
                        //world.spawn(creature.getLocation(), creatureType.getEntityClass(), SpawnReason.NATURAL);
                    }
                }
            }
        }
    }

}
