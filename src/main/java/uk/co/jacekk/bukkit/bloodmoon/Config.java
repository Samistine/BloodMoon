package uk.co.jacekk.bukkit.bloodmoon;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

import uk.co.jacekk.bukkit.baseplugin.config.PluginConfigKey;

public class Config {

    public static final PluginConfigKey ENABLED = new PluginConfigKey("enabled", false);
    public static final PluginConfigKey ALWAYS_ON = new PluginConfigKey("always-on", false);
    public static final PluginConfigKey CHANCE = new PluginConfigKey("chance", 14);

    public static final PluginConfigKey FEATURE_CHAT_MESSAGE_ENABLED = new PluginConfigKey("features.chat-message.enabled", true);
    public static final PluginConfigKey FEATURE_CHAT_MESSAGE_START_MESSAGE = new PluginConfigKey("features.chat-message.start-message", "&cThe bloodmoon is rising !");
    public static final PluginConfigKey FEATURE_CHAT_MESSAGE_END_MESSAGE = new PluginConfigKey("features.chat-message.end-message", "&cThe bloodmoon is over !");

    public static final PluginConfigKey FEATURE_SERVER_COMMANDS_ENABLED = new PluginConfigKey("features.server-commands.enabled", false);
    public static final PluginConfigKey FEATURE_SERVER_COMMANDS_START_COMMANDS = new PluginConfigKey("features.server-commands.commands.start", Arrays.asList("toggledownfall", "time set 0", "op Notch"));
    public static final PluginConfigKey FEATURE_SERVER_COMMANDS_END_COMMANDS = new PluginConfigKey("features.server-commands.commands.end", Arrays.asList("toggledownfall", "time set 12000", "deop Notch"));

    public static final PluginConfigKey FEATURE_DISABLED_COMMANDS_ENABLED = new PluginConfigKey("features.disabled-commands.enabled", true);
    public static final PluginConfigKey FEATURE_DISABLED_COMMANDS_COMMANDS = new PluginConfigKey("features.disabled-commands.commands", Arrays.asList("spawn", "home"));

    public static final PluginConfigKey FEATURE_PLAY_SOUND_ENABLED = new PluginConfigKey("features.play-sound.enabled", true);
    public static final PluginConfigKey FEATURE_PLAY_SOUND_SOUND = new PluginConfigKey("features.play-sound.sound", "ENTITY_WITHER_SPAWN");
    public static final PluginConfigKey FEATURE_PLAY_SOUND_PITCH = new PluginConfigKey("features.play-sound.pitch", 1.0d);
    public static final PluginConfigKey FEATURE_PLAY_SOUND_VOLUME = new PluginConfigKey("features.play-sound.volume", 1.0d);

    public static final PluginConfigKey FEATURE_ARROW_RATE_ENABLED = new PluginConfigKey("features.arrow-rate.enabled", true);
    public static final PluginConfigKey FEATURE_ARROW_RATE_MULTIPLIER = new PluginConfigKey("features.arrow-rate.multiplier", 2);

    public static final PluginConfigKey FEATURE_FIRE_ARROWS_ENABLED = new PluginConfigKey("features.fire-arrows.enabled", true);
    public static final PluginConfigKey FEATURE_FIRE_ARROWS_CHANCE = new PluginConfigKey("features.fire-arrows.chance", 100);
    public static final PluginConfigKey FEATURE_FIRE_ARROWS_IGNITE_TARGET = new PluginConfigKey("features.fire-arrows.ignite-target", true);

    public static final PluginConfigKey FEATURE_ZOMBIE_WEAPON_ENABLED = new PluginConfigKey("features.zombie-weapon.enabled", true);
    public static final PluginConfigKey FEATURE_ZOMBIE_WEAPON_CHANCE = new PluginConfigKey("features.zombie-weapon.chance", 60);
    public static final PluginConfigKey FEATURE_ZOMBIE_WEAPON_DROP_CHANCE = new PluginConfigKey("features.zombie-weapon.drop-chance", 25);
    public static final PluginConfigKey FEATURE_ZOMBIE_WEAPON_IGNORE_SPAWNERS = new PluginConfigKey("features.zombie-weapon.ignore-spawners", true);
    public static final PluginConfigKey FEATURE_ZOMBIE_WEAPON_WEAPONS = new PluginConfigKey("features.zombie-weapon.weapons", Arrays.asList("DIAMOND_SWORD", "GOLD_SWORD", "IRON_SWORD"));

    public static final PluginConfigKey FEATURE_ZOMBIE_ARMOR_ENABLED = new PluginConfigKey("features.zombie-armor.enabled", true);
    public static final PluginConfigKey FEATURE_ZOMBIE_ARMOR_CHANCE = new PluginConfigKey("features.zombie-armor.chance", 60);
    public static final PluginConfigKey FEATURE_ZOMBIE_ARMOR_DROP_CHANCE = new PluginConfigKey("features.zombie-armor.drop-chance", 7);
    public static final PluginConfigKey FEATURE_ZOMBIE_ARMOR_IGNORE_SPAWNERS = new PluginConfigKey("features.zombie-armor.ignore-spawners", true);
    public static final PluginConfigKey FEATURE_ZOMBIE_ARMOR_ARMOR = new PluginConfigKey("features.zombie-armor.armor", Arrays.asList("DIAMOND", "GOLD", "IRON"));

    public static final PluginConfigKey FEATURE_TARGET_DISTANCE_ENABLED = new PluginConfigKey("features.target-distance.enabled", true);
    public static final PluginConfigKey FEATURE_TARGET_DISTANCE_MULTIPLIER = new PluginConfigKey("features.target-distance.multiplier", 2.0d);
    public static final PluginConfigKey FEATURE_TARGET_DISTANCE_MOBS = new PluginConfigKey("features.target-distance.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER"));

    public static final PluginConfigKey FEATURE_MOVEMENT_SPEED_ENABLED = new PluginConfigKey("features.movement-speed.enabled", true);
    public static final PluginConfigKey FEATURE_MOVEMENT_SPEED_MULTIPLIER = new PluginConfigKey("features.movement-speed.multiplier", 1.20d);
    public static final PluginConfigKey FEATURE_MOVEMENT_SPEED_FAST_CHANCE = new PluginConfigKey("features.movement-speed.fast-chance", 15);
    public static final PluginConfigKey FEATURE_MOVEMENT_SPEED_FAST_MULTIPLIER = new PluginConfigKey("features.movement-speed.fast-multiplier", 1.3d);
    public static final PluginConfigKey FEATURE_MOVEMENT_SPEED_MOBS = new PluginConfigKey("features.movement-speed.mobs", Arrays.asList("ZOMBIE", "SKELETON", "CREEPER"));

    public static final PluginConfigKey FEATURE_BREAK_BLOCKS_ENABLED = new PluginConfigKey("features.break-blocks.enabled", true);
    public static final PluginConfigKey FEATURE_BREAK_BLOCKS_DROP_ITEMS = new PluginConfigKey("features.break-blocks.drop-items", true);
    public static final PluginConfigKey FEATURE_BREAK_BLOCKS_REALISTIC_DROP = new PluginConfigKey("features.break-blocks.realistic-drop", true);
    public static final PluginConfigKey FEATURE_BREAK_BLOCKS_MOBS = new PluginConfigKey("features.break-blocks.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));
    public static final PluginConfigKey FEATURE_BREAK_BLOCKS_BLOCKS = new PluginConfigKey("features.break-blocks.blocks", Arrays.asList("WOOD", "LOG", "GLASS"));

    public static final PluginConfigKey FEATURE_MAX_HEALTH_ENABLED = new PluginConfigKey("features.max-health.enabled", true);
    public static final PluginConfigKey FEATURE_MAX_HEALTH_MULTIPLIER = new PluginConfigKey("features.max-health.multiplier", 2.0d);
    public static final PluginConfigKey FEATURE_MAX_HEALTH_MOBS = new PluginConfigKey("features.max-health.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));

    public static final PluginConfigKey FEATURE_MORE_SPAWNING_ENABLED = new PluginConfigKey("features.more-spawning.enabled", true);
    public static final PluginConfigKey FEATURE_MORE_SPAWNING_MULTIPLIER = new PluginConfigKey("features.more-spawning.multiplier", 2);
    public static final PluginConfigKey FEATURE_MORE_SPAWNING_MOBS = new PluginConfigKey("features.more-spawning.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));

    public static final PluginConfigKey FEATURE_MORE_EXP_ENABLED = new PluginConfigKey("features.more-exp.enabled", true);
    public static final PluginConfigKey FEATURE_MORE_EXP_IGNORE_SPAWNERS = new PluginConfigKey("features.more-exp.ignore-spawners", false);
    public static final PluginConfigKey FEATURE_MORE_EXP_MULTIPLIER = new PluginConfigKey("features.more-exp.multiplier", 2);

    public static final PluginConfigKey FEATURE_MORE_DROPS_ENABLED = new PluginConfigKey("features.more-drops.enabled", true);
    public static final PluginConfigKey FEATURE_MORE_DROPS_IGNORE_SPAWNERS = new PluginConfigKey("features.more-drops.ignore-spawners", false);
    public static final PluginConfigKey FEATURE_MORE_DROPS_MULTIPLIER = new PluginConfigKey("features.more-drops.multiplier", 2);

    public static final PluginConfigKey FEATURE_SWORD_DAMAGE_ENABLED = new PluginConfigKey("features.sword-damage.enabled", true);
    public static final PluginConfigKey FEATURE_SWORD_DAMAGE_MOBS = new PluginConfigKey("features.sword-damage.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));
    public static final PluginConfigKey FEATURE_SWORD_DAMAGE_CHANCE = new PluginConfigKey("features.sword-damage.chance", 10);

    public static final PluginConfigKey FEATURE_SUPER_CREEPERS_ENABLED = new PluginConfigKey("features.super-creepers.enabled", true);
    public static final PluginConfigKey FEATURE_SUPER_CREEPERS_POWER = new PluginConfigKey("features.super-creepers.power", 4.0D);
    public static final PluginConfigKey FEATURE_SUPER_CREEPERS_FIRE = new PluginConfigKey("features.super-creepers.fire", true);
    public static final PluginConfigKey FEATURE_SUPER_CREEPERS_LIGHTNING = new PluginConfigKey("features.super-creepers.lightning", true);

    public static final PluginConfigKey FEATURE_SPAWN_ON_KILL_ENABLED = new PluginConfigKey("features.spawn-on-kill.enabled", true);
    public static final PluginConfigKey FEATURE_SPAWN_ON_KILL_MOBS = new PluginConfigKey("features.spawn-on-kill.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));
    public static final PluginConfigKey FEATURE_SPAWN_ON_KILL_CHANCE = new PluginConfigKey("features.spawn-on-kill.chance", 10);
    public static final PluginConfigKey FEATURE_SPAWN_ON_KILL_SPAWN = new PluginConfigKey("features.spawn-on-kill.spawn", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));

    public static final PluginConfigKey FEATURE_SPAWN_ON_SLEEP_ENABLED = new PluginConfigKey("features.spawn-on-sleep.enabled", true);
    public static final PluginConfigKey FEATURE_SPAWN_ON_SLEEP_SPAWN = new PluginConfigKey("features.spawn-on-sleep.spawn", Arrays.asList("ZOMBIE"));

    public static final PluginConfigKey FEATURE_SPAWN_CONTROL_ENABLED = new PluginConfigKey("features.spawn-control.enabled", true);
    public static final PluginConfigKey FEATURE_SPAWN_CONTROL_SPAWN = new PluginConfigKey("features.spawn-control.spawn", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN", "PIG_ZOMBIE", "BLAZE", "MAGMA_CUBE"));

    public static final PluginConfigKey FEATURE_LOCK_IN_WORLD_ENABLED = new PluginConfigKey("features.lock-in-world.enabled", false);

    public static final PluginConfigKey FEATURE_TEXTURE_PACK_ENABLED = new PluginConfigKey("features.texture-pack.enabled", false);
    public static final PluginConfigKey FEATURE_TEXTURE_PACK_NORMAL = new PluginConfigKey("features.texture-pack.normal", "http://bukkit.jacekk.co.uk/bloodmoon_tps/normal.zip");
    public static final PluginConfigKey FEATURE_TEXTURE_PACK_BLOODMOON = new PluginConfigKey("features.texture-pack.bloodmoon", "http://bukkit.jacekk.co.uk/bloodmoon_tps/bloodmoon.zip");

    public static final PluginConfigKey FEATURE_EXTENDED_NIGHT_ENABLED = new PluginConfigKey("features.extended-night.enabled", true);
    public static final PluginConfigKey FEATURE_EXTENDED_NIGHT_MIN_KILLS = new PluginConfigKey("features.extended-night.min-kills", 16);

    public static final PluginConfigKey FEATURE_WEATHER_ENABLED = new PluginConfigKey("features.weather.enabled", true);
    public static final PluginConfigKey FEATURE_WEATHER_THUNDER = new PluginConfigKey("features.weather.thunder", true);
    public static final PluginConfigKey FEATURE_WEATHER_RAIN = new PluginConfigKey("features.weather.rain", true);
    public static final PluginConfigKey FEATURE_WEATHER_CHANCE = new PluginConfigKey("features.weather.chance", 50);

    public static final PluginConfigKey FEATURE_DAYLIGHT_PROOF_MOBS_ENABLED = new PluginConfigKey("features.daylight-proof-mobs.enabled", true);

    public static final PluginConfigKey FEATURE_NETHER_SKY_ENABLED = new PluginConfigKey("features.nether-sky.enabled", false);

    public static final PluginConfigKey FEATURE_DUNGEONS_ENABLED = new PluginConfigKey("features.dungeons.enabled", true);
    public static final PluginConfigKey FEATURE_DUNGEONS_PROTECTED = new PluginConfigKey("features.dungeons.protected", true);
    public static final PluginConfigKey FEATURE_DUNGEONS_BIOMES = new PluginConfigKey("features.dungeons.biomes", Arrays.asList(Biome.PLAINS.name(), Biome.ICE_FLATS.name(), Biome.DESERT.name(), Biome.SWAMPLAND.name()));
    public static final PluginConfigKey FEATURE_DUNGEONS_CHANCE = new PluginConfigKey("features.dungeons.chance", 10);
    public static final PluginConfigKey FEATURE_DUNGEONS_MIN_LAYERS = new PluginConfigKey("features.dungeons.min-layers", 3);
    public static final PluginConfigKey FEATURE_DUNGEONS_MAX_LAYERS = new PluginConfigKey("features.dungeons.max-layers", 5);
    public static final PluginConfigKey FEATURE_DUNGEONS_SPAWNER_TYPES = new PluginConfigKey("features.dungeons.spawner-types", Arrays.asList(EntityType.ZOMBIE.name(), EntityType.SKELETON.name()));
    public static final PluginConfigKey FEATURE_DUNGEONS_SPAWNER_DELAY = new PluginConfigKey("features.dungeons.spawner-delay", 100);
    public static final PluginConfigKey FEATURE_DUNGEONS_SPAWNER_COUNT = new PluginConfigKey("features.dungeons.spawner-count", 6);
    public static final PluginConfigKey FEATURE_DUNGEONS_SPAWNER_MAX_MOBS = new PluginConfigKey("features.dungeons.spawner-max-mobs", 8);
    public static final PluginConfigKey FEATURE_DUNGEONS_CHEST_ITEMS = new PluginConfigKey("features.dungeons.chest-items", Arrays.asList(Material.BREAD.name(), Material.APPLE.name(), Material.PORK.name(), Material.SADDLE.name(), Material.BUCKET.name(), Material.STRING.name(), Material.REDSTONE.name(), Material.SULPHUR.name(), Material.COCOA.name()));
    public static final PluginConfigKey FEATURE_DUNGEONS_MIN_STACK_SIZE = new PluginConfigKey("features.dungeons.min-stack-size", 1);
    public static final PluginConfigKey FEATURE_DUNGEONS_MAX_STACK_SIZE = new PluginConfigKey("features.dungeons.max-stack-size", 8);
    public static final PluginConfigKey FEATURE_DUNGEONS_ITEMS_PER_CHEST = new PluginConfigKey("features.dungeons.items-per-chest", 12);

    public static final PluginConfigKey FEATURE_GIANTS_ENABLED = new PluginConfigKey("features.giants.enabled", false);
    public static final PluginConfigKey FEATURE_GIANTS_BREAK_BLOCKS = new PluginConfigKey("features.giants.break-blocks", Arrays.asList(Material.GRASS.name(), Material.LEAVES.name(), Material.WOOD.name(), Material.GLASS.name(), Material.CROPS.name(), Material.SOIL.name(), Material.LOG.name(), Material.WOOD_STEP.name(), Material.WOOD_STAIRS.name()));

}
