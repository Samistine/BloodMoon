package com.github.hexosse.bloodmoon.configuration;

import com.github.hexosse.bloodmoon.BloodMoon;
import com.github.hexosse.pluginframework.pluginapi.config.ConfigKey;
import com.github.hexosse.pluginframework.pluginapi.config.ConfigKeyFile;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class WorldConfig extends ConfigKeyFile<BloodMoon>
{
	public final ConfigKey<Boolean> ENABLED = new ConfigKey("enabled", false);
	public final ConfigKey<Boolean> ALWAYS_ON = new ConfigKey("always-on", false);
	public final ConfigKey<Integer> CHANCE = new ConfigKey("chance", 14);

	public final ConfigKey<Boolean> FEATURE_CHAT_MESSAGE_ENABLED = new ConfigKey("features.chat-message.enabled", true);
	public final ConfigKey<String> FEATURE_CHAT_MESSAGE_START_MESSAGE = new ConfigKey("features.chat-message.start-message", "&cThe bloodmoon is rising !");
	public final ConfigKey<String> FEATURE_CHAT_MESSAGE_END_MESSAGE = new ConfigKey("features.chat-message.end-message", "&cThe bloodmoon is over !");

	public final ConfigKey<Boolean> FEATURE_SERVER_COMMANDS_ENABLED = new ConfigKey("features.server-commands.enabled", false);
	public final ConfigKey<List<String>> FEATURE_SERVER_COMMANDS_START_COMMANDS = new ConfigKey("features.server-commands.commands.start", Arrays.asList("toggledownfall", "time set 0", "op Notch"));
	public final ConfigKey<List<String>> FEATURE_SERVER_COMMANDS_END_COMMANDS = new ConfigKey("features.server-commands.commands.end", Arrays.asList("toggledownfall", "time set 12000", "deop Notch"));

	public final ConfigKey<Boolean> FEATURE_DISABLED_COMMANDS_ENABLED = new ConfigKey("features.disabled-commands.enabled", true);
	public final ConfigKey<List<String>> FEATURE_DISABLED_COMMANDS_COMMANDS = new ConfigKey("features.disabled-commands.commands", Arrays.asList("spawn", "home", "f home"));

	public final ConfigKey<Boolean> FEATURE_PLAY_SOUND_ENABLED = new ConfigKey("features.play-sound.enabled", true);
	public final ConfigKey<String> FEATURE_PLAY_SOUND_SOUND = new ConfigKey("features.play-sound.sound", "ENTITY_WITHER_SPAWN");
	public final ConfigKey<Float> FEATURE_PLAY_SOUND_PITCH = new ConfigKey("features.play-sound.pitch", 1.0f);
	public final ConfigKey<Float> FEATURE_PLAY_SOUND_VOLUME = new ConfigKey("features.play-sound.volume", 1.0f);

	public final ConfigKey<Boolean> FEATURE_ARROW_RATE_ENABLED = new ConfigKey("features.arrow-rate.enabled", true);
	public final ConfigKey<Integer> FEATURE_ARROW_RATE_MULTIPLIER = new ConfigKey("features.arrow-rate.multiplier", 2);

	public final ConfigKey<Boolean> FEATURE_FIRE_ARROWS_ENABLED = new ConfigKey("features.fire-arrows.enabled", true);
	public final ConfigKey<Integer> FEATURE_FIRE_ARROWS_CHANCE = new ConfigKey("features.fire-arrows.chance", 100);
	public final ConfigKey<Boolean> FEATURE_FIRE_ARROWS_IGNITE_TARGET = new ConfigKey("features.fire-arrows.ignite-target", true);

	public final ConfigKey<Boolean> FEATURE_ZOMBIE_WEAPON_ENABLED = new ConfigKey("features.zombie-weapon.enabled", true);
	public final ConfigKey<Integer> FEATURE_ZOMBIE_WEAPON_CHANCE = new ConfigKey("features.zombie-weapon.chance", 60);
	public final ConfigKey<Float> FEATURE_ZOMBIE_WEAPON_DROP_CHANCE = new ConfigKey("features.zombie-weapon.drop-chance", 25.0f);
	public final ConfigKey<Boolean> FEATURE_ZOMBIE_WEAPON_IGNORE_SPAWNERS = new ConfigKey("features.zombie-weapon.ignore-spawners", true);
	public final ConfigKey<List<String>> FEATURE_ZOMBIE_WEAPON_WEAPONS = new ConfigKey("features.zombie-weapon.weapons", Arrays.asList("DIAMOND_SWORD", "GOLD_SWORD", "IRON_SWORD"));

	public final ConfigKey<Boolean> FEATURE_ZOMBIE_ARMOR_ENABLED = new ConfigKey("features.zombie-armor.enabled", true);
	public final ConfigKey<Integer> FEATURE_ZOMBIE_ARMOR_CHANCE = new ConfigKey("features.zombie-armor.chance", 60);
	public final ConfigKey<Integer> FEATURE_ZOMBIE_ARMOR_DROP_CHANCE = new ConfigKey("features.zombie-armor.drop-chance", 7);
	public final ConfigKey<Boolean> FEATURE_ZOMBIE_ARMOR_IGNORE_SPAWNERS = new ConfigKey("features.zombie-armor.ignore-spawners", true);
	public final ConfigKey<List<String>> FEATURE_ZOMBIE_ARMOR_ARMOR = new ConfigKey("features.zombie-armor.armor", Arrays.asList("DIAMOND", "GOLD", "IRON"));

	public final ConfigKey<Boolean> FEATURE_TARGET_DISTANCE_ENABLED = new ConfigKey("features.target-distance.enabled", true);
	public final ConfigKey<Double> FEATURE_TARGET_DISTANCE_MULTIPLIER = new ConfigKey("features.target-distance.multiplier", 2.0d);
	public final ConfigKey<List<String>> FEATURE_TARGET_DISTANCE_MOBS = new ConfigKey("features.target-distance.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER"));

	public final ConfigKey<Boolean> FEATURE_MOVEMENT_SPEED_ENABLED = new ConfigKey("features.movement-speed.enabled", true);
	public final ConfigKey<Double> FEATURE_MOVEMENT_SPEED_MULTIPLIER = new ConfigKey("features.movement-speed.multiplier", 1.20d);
	public final ConfigKey<Integer> FEATURE_MOVEMENT_SPEED_FAST_CHANCE = new ConfigKey("features.movement-speed.fast-chance", 15);
	public final ConfigKey<Double> FEATURE_MOVEMENT_SPEED_FAST_MULTIPLIER = new ConfigKey("features.movement-speed.fast-multiplier", 1.3d);
	public final ConfigKey<List<String>> FEATURE_MOVEMENT_SPEED_MOBS = new ConfigKey("features.movement-speed.mobs", Arrays.asList("ZOMBIE", "SKELETON", "CREEPER"));

	public final ConfigKey<Boolean> FEATURE_BREAK_BLOCKS_ENABLED = new ConfigKey("features.break-blocks.enabled", true);
	public final ConfigKey<Boolean> FEATURE_BREAK_BLOCKS_DROP_ITEMS = new ConfigKey("features.break-blocks.drop-items", true);
	public final ConfigKey<Boolean> FEATURE_BREAK_BLOCKS_REALISTIC_DROP = new ConfigKey("features.break-blocks.realistic-drop", true);
	public final ConfigKey<List<String>> FEATURE_BREAK_BLOCKS_MOBS = new ConfigKey("features.break-blocks.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));
	public final ConfigKey<List<String>> FEATURE_BREAK_BLOCKS_BLOCKS = new ConfigKey("features.break-blocks.blocks", Arrays.asList("WOOD", "LOG", "GLASS"));

	public final ConfigKey<Boolean> FEATURE_MAX_HEALTH_ENABLED = new ConfigKey("features.max-health.enabled", true);
	public final ConfigKey<Double> FEATURE_MAX_HEALTH_MULTIPLIER = new ConfigKey("features.max-health.multiplier", 2.0d);
	public final ConfigKey<List<String>> FEATURE_MAX_HEALTH_MOBS = new ConfigKey("features.max-health.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));

	public final ConfigKey<Boolean> FEATURE_MORE_SPAWNING_ENABLED = new ConfigKey("features.more-spawning.enabled", true);
	public final ConfigKey<Integer> FEATURE_MORE_SPAWNING_MULTIPLIER = new ConfigKey("features.more-spawning.multiplier", 2);
	public final ConfigKey<List<String>> FEATURE_MORE_SPAWNING_MOBS = new ConfigKey("features.more-spawning.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));

	public final ConfigKey<Boolean> FEATURE_MORE_EXP_ENABLED = new ConfigKey("features.more-exp.enabled", true);
	public final ConfigKey<Boolean> FEATURE_MORE_EXP_IGNORE_SPAWNERS = new ConfigKey("features.more-exp.ignore-spawners", false);
	public final ConfigKey<Integer> FEATURE_MORE_EXP_MULTIPLIER = new ConfigKey("features.more-exp.multiplier", 2);

	public final ConfigKey<Boolean> FEATURE_MORE_DROPS_ENABLED = new ConfigKey("features.more-drops.enabled", true);
	public final ConfigKey<Boolean> FEATURE_MORE_DROPS_IGNORE_SPAWNERS = new ConfigKey("features.more-drops.ignore-spawners", false);
	public final ConfigKey<Integer> FEATURE_MORE_DROPS_MULTIPLIER = new ConfigKey("features.more-drops.multiplier", 2);

	public final ConfigKey<Boolean> FEATURE_SWORD_DAMAGE_ENABLED = new ConfigKey("features.sword-damage.enabled", true);
	public final ConfigKey<List<String>> FEATURE_SWORD_DAMAGE_MOBS = new ConfigKey("features.sword-damage.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));
	public final ConfigKey<Integer> FEATURE_SWORD_DAMAGE_CHANCE = new ConfigKey("features.sword-damage.chance", 10);

	public final ConfigKey<Boolean> FEATURE_SUPER_CREEPERS_ENABLED = new ConfigKey("features.super-creepers.enabled", true);
	public final ConfigKey<Float> FEATURE_SUPER_CREEPERS_POWER = new ConfigKey("features.super-creepers.power", 4.0f);
	public final ConfigKey<Boolean> FEATURE_SUPER_CREEPERS_FIRE = new ConfigKey("features.super-creepers.fire", true);
	public final ConfigKey<Boolean> FEATURE_SUPER_CREEPERS_LIGHTNING = new ConfigKey("features.super-creepers.lightning", true);

	public final ConfigKey<Boolean> FEATURE_SPAWN_ON_KILL_ENABLED = new ConfigKey("features.spawn-on-kill.enabled", true);
	public final ConfigKey<List<String>> FEATURE_SPAWN_ON_KILL_MOBS = new ConfigKey("features.spawn-on-kill.mobs", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));
	public final ConfigKey<Integer> FEATURE_SPAWN_ON_KILL_CHANCE = new ConfigKey("features.spawn-on-kill.chance", 10);
	public final ConfigKey<List<String>> FEATURE_SPAWN_ON_KILL_SPAWN = new ConfigKey("features.spawn-on-kill.spawn", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN"));

	public final ConfigKey<Boolean> FEATURE_SPAWN_ON_SLEEP_ENABLED = new ConfigKey("features.spawn-on-sleep.enabled", true);
	public final ConfigKey<List<String>> FEATURE_SPAWN_ON_SLEEP_SPAWN = new ConfigKey("features.spawn-on-sleep.spawn", Arrays.asList("ZOMBIE"));

	public final ConfigKey<Boolean> FEATURE_SPAWN_CONTROL_ENABLED = new ConfigKey("features.spawn-control.enabled", true);
	public final ConfigKey<List<String>> FEATURE_SPAWN_CONTROL_SPAWN = new ConfigKey("features.spawn-control.spawn", Arrays.asList("ZOMBIE", "SKELETON", "SPIDER", "CREEPER", "ENDERMAN", "PIG_ZOMBIE", "BLAZE", "MAGMA_CUBE"));

	public final ConfigKey<Boolean> FEATURE_LOCK_IN_WORLD_ENABLED = new ConfigKey("features.lock-in-world.enabled", false);

	public final ConfigKey<Boolean> FEATURE_TEXTURE_PACK_ENABLED = new ConfigKey("features.texture-pack.enabled", false);
	public final ConfigKey<String> FEATURE_TEXTURE_PACK_NORMAL = new ConfigKey("features.texture-pack.normal", "https://raw.githubusercontent.com/hexosse/bloodmoon/master/src/main/texture_pack/normal.zip");
	public final ConfigKey<String> FEATURE_TEXTURE_PACK_BLOODMOON = new ConfigKey("features.texture-pack.bloodmoon", "http://raw.githubusercontent.com/hexosse/bloodmoon/master/src/main/texture_pack/bloodmoon.zip");

	public final ConfigKey<Boolean> FEATURE_EXTENDED_NIGHT_ENABLED = new ConfigKey("features.extended-night.enabled", true);
	public final ConfigKey<Integer> FEATURE_EXTENDED_NIGHT_MIN_KILLS = new ConfigKey("features.extended-night.min-kills", 16);

	public final ConfigKey<Boolean> FEATURE_WEATHER_ENABLED = new ConfigKey("features.weather.enabled", true);
	public final ConfigKey<Boolean> FEATURE_WEATHER_THUNDER = new ConfigKey("features.weather.thunder", true);
	public final ConfigKey<Boolean> FEATURE_WEATHER_RAIN = new ConfigKey("features.weather.rain", true);
	public final ConfigKey<Integer> FEATURE_WEATHER_CHANCE = new ConfigKey("features.weather.chance", 50);

	public final ConfigKey<Boolean> FEATURE_DAYLIGHT_PROOF_MOBS_ENABLED = new ConfigKey("features.daylight-proof-mobs.enabled", true);

	public final ConfigKey<Boolean> FEATURE_NETHER_SKY_ENABLED = new ConfigKey("features.nether-sky.enabled", false);

	public final ConfigKey<Boolean> FEATURE_DUNGEONS_ENABLED = new ConfigKey("features.dungeons.enabled", true);
	public final ConfigKey<Boolean> FEATURE_DUNGEONS_PROTECTED = new ConfigKey("features.dungeons.protected", true);
	public final ConfigKey<List<String>> FEATURE_DUNGEONS_BIOMES = new ConfigKey("features.dungeons.biomes", Arrays.asList(Biome.PLAINS.name(), Biome.ICE_FLATS.name(), Biome.DESERT.name(), Biome.SWAMPLAND.name()));
	public final ConfigKey<Integer> FEATURE_DUNGEONS_CHANCE = new ConfigKey("features.dungeons.chance", 10);
	public final ConfigKey<Integer> FEATURE_DUNGEONS_MIN_LAYERS = new ConfigKey("features.dungeons.min-layers", 3);
	public final ConfigKey<Integer> FEATURE_DUNGEONS_MAX_LAYERS = new ConfigKey("features.dungeons.max-layers", 5);
	public final ConfigKey<List<String>> FEATURE_DUNGEONS_SPAWNER_TYPES = new ConfigKey("features.dungeons.spawner-types", Arrays.asList(EntityType.ZOMBIE.name(), EntityType.SKELETON.name()));
	public final ConfigKey<Integer> FEATURE_DUNGEONS_SPAWNER_DELAY = new ConfigKey("features.dungeons.spawner-delay", 100);
	public final ConfigKey<Integer> FEATURE_DUNGEONS_SPAWNER_COUNT = new ConfigKey("features.dungeons.spawner-count", 6);
	public final ConfigKey<Integer> FEATURE_DUNGEONS_SPAWNER_MAX_MOBS = new ConfigKey("features.dungeons.spawner-max-mobs", 8);
	public final ConfigKey<List<String>> FEATURE_DUNGEONS_CHEST_ITEMS = new ConfigKey("features.dungeons.chest-items", Arrays.asList(Material.BREAD.name(), Material.APPLE.name(), Material.PORK.name(), Material.SADDLE.name(), Material.BUCKET.name(), Material.STRING.name(), Material.REDSTONE.name(), Material.SULPHUR.name(), Material.COCOA.name()));
	public final ConfigKey<Integer> FEATURE_DUNGEONS_MIN_STACK_SIZE = new ConfigKey("features.dungeons.min-stack-size", 1);
	public final ConfigKey<Integer> FEATURE_DUNGEONS_MAX_STACK_SIZE = new ConfigKey("features.dungeons.max-stack-size", 8);
	public final ConfigKey<Integer> FEATURE_DUNGEONS_ITEMS_PER_CHEST = new ConfigKey("features.dungeons.items-per-chest", 12);

	public final ConfigKey<Boolean> FEATURE_GIANTS_ENABLED = new ConfigKey("features.giants.enabled", true);
	public final ConfigKey<List<String>> FEATURE_GIANTS_BREAK_BLOCKS = new ConfigKey("features.giants.break-blocks", Arrays.asList(Material.GRASS.name(), Material.LEAVES.name(), Material.WOOD.name(), Material.GLASS.name(), Material.CROPS.name(), Material.SOIL.name(), Material.LOG.name(), Material.WOOD_STEP.name(), Material.WOOD_STAIRS.name()));

	public final ConfigKey<Boolean> INTEGRATION_FACTIONS_PREVENT_SPAWING = new ConfigKey("features.integration.factions.prevent-spawing", true);


	public WorldConfig(BloodMoon plugin, File dataFolder, String filename)
	{
		super(plugin, new File(dataFolder, filename));
		load();
	}
}
