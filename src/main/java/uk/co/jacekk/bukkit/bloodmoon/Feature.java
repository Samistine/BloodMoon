package uk.co.jacekk.bukkit.bloodmoon;

import org.bukkit.event.Listener;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfigKey;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.client.ChatMessageListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.client.PlaySoundListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.client.TexturePackListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.DaylightProofMobsListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.FireArrowsListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.MaxHealthListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.MoreDropsListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.MoreExpListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.MovementSpeedListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.SuperCreepersListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.TargetDistanceListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.ZombieArmorListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.mob.ZombieWeaponListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.player.SwordDamageListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.server.DisabledCommandsListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.server.ServerCommandsListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.spawning.GiantsListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.spawning.MoreMobsListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.spawning.MoreSpawningListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.spawning.SpawnOnKillListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.spawning.SpawnOnSleepListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.world.DungeonListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.world.ExtendedNightListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.world.LockInWorldListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.world.NetherSkyListener;
import uk.co.jacekk.bukkit.bloodmoon.feature.world.WeatherListener;

public enum Feature {
	
	// Mob features
	SUPER_CREEPERS(SuperCreepersListener.class, Config.FEATURE_SUPER_CREEPERS_ENABLED),
	ZOMBIE_ARMOR(ZombieArmorListener.class, Config.FEATURE_ZOMBIE_ARMOR_ENABLED),
	ZOMBIE_WEAPON(ZombieWeaponListener.class, Config.FEATURE_ZOMBIE_WEAPON_ENABLED),
	DAYLIGHT_PROOF_MOBS(DaylightProofMobsListener.class, Config.FEATURE_DAYLIGHT_PROOF_MOBS_ENABLED),
	FIRE_ARROWS(FireArrowsListener.class, Config.FEATURE_FIRE_ARROWS_ENABLED),
	MAX_HEALTH(MaxHealthListener.class, Config.FEATURE_MAX_HEALTH_ENABLED),
	MORE_DROPS(MoreDropsListener.class, Config.FEATURE_MORE_DROPS_ENABLED),
	MORE_EXP(MoreExpListener.class, Config.FEATURE_MORE_EXP_ENABLED),
	BREAK_BLOCKS(null, Config.FEATURE_BREAK_BLOCKS_ENABLED), // handled in BloodMoonEntity*
	TARGET_DISTANCE(TargetDistanceListener.class, Config.FEATURE_TARGET_DISTANCE_ENABLED),
	ARROW_RATE(null, Config.FEATURE_ARROW_RATE_ENABLED), // handled in BloodMoonPathfinderGoalArrowAttack
	MOVEMENT_SPEED(MovementSpeedListener.class, Config.FEATURE_MOVEMENT_SPEED_ENABLED),
	
	// World features
	WEATHER(WeatherListener.class, Config.FEATURE_WEATHER_ENABLED),
	NETHER_SKY(NetherSkyListener.class, Config.FEATURE_NETHER_SKY_ENABLED),
	LOCK_IN_WORLD(LockInWorldListener.class, Config.FEATURE_LOCK_IN_WORLD_ENABLED),
	EXTENDED_NIGHT(ExtendedNightListener.class, Config.FEATURE_EXTENDED_NIGHT_ENABLED),
	DUNGEONS(DungeonListener.class, Config.FEATURE_DUNGEONS_ENABLED),
	
	// Spawning features
	SPAWN_ON_KILL(SpawnOnKillListener.class, Config.FEATURE_SPAWN_ON_KILL_ENABLED),
	SPAWN_ON_SLEEP(SpawnOnSleepListener.class, Config.FEATURE_SPAWN_ON_SLEEP_ENABLED),
	MORE_SPAWNING(MoreSpawningListener.class, Config.FEATURE_MORE_SPAWNING_ENABLED),
	SPAWN_CONTROL(MoreMobsListener.class, Config.FEATURE_SPAWN_CONTROL_ENABLED), // partially handled in ChunkProviderServer
	GIANTS(GiantsListener.class, Config.FEATURE_GIANTS_ENABLED),
	
	// Server features
	SERVER_COMMANDS(ServerCommandsListener.class, Config.FEATURE_SERVER_COMMANDS_ENABLED),
	DISABLED_COMMANDS(DisabledCommandsListener.class, Config.FEATURE_DISABLED_COMMANDS_ENABLED),
	
	// Player features
	SWORD_DAMAGE(SwordDamageListener.class, Config.FEATURE_SWORD_DAMAGE_ENABLED),
	
	// Client Features.
	PLAY_SOUND(PlaySoundListener.class, Config.FEATURE_PLAY_SOUND_ENABLED),
	CHAT_MESSAGE(ChatMessageListener.class, Config.FEATURE_CHAT_MESSAGE_ENABLED),
	TEXTURE_PACK(TexturePackListener.class, Config.FEATURE_TEXTURE_PACK_ENABLED);
	
	//private Class<? extends BaseListener<BloodMoon>> listenerClass;
        private final Class<? extends Listener> listenerClass;
	private final PluginConfigKey enabledConfigKey;
	
	/*private Feature(Class<? extends BaseListener<BloodMoon>> listenerClass, PluginConfigKey enabledConfigKey){
		this.listenerClass = listenerClass;
		this.enabledConfigKey = enabledConfigKey;
	}
	
	public Class<? extends BaseListener<BloodMoon>> getListenerClass(){
		return this.listenerClass;
	}*/
        
        private Feature(Class<? extends Listener> listenerClass, PluginConfigKey enabledConfigKey){
		this.listenerClass = listenerClass;
		this.enabledConfigKey = enabledConfigKey;
	}
	
	public Class<? extends Listener> getListenerClass(){
		return this.listenerClass;
	}
	
	public PluginConfigKey getEnabledConfigKey(){
		return this.enabledConfigKey;
	}
	
}
