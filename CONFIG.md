## Config
All aspects of the blood moon can be configured, below is an example of a config file which would enable all of the features. More details on each section can be found below. Each section contains an enabled setting, this controls if that feature will be used or not. You will almost certainly want to configure the plugin to your needs, by default everything is enabled. One of these files will be created for each world that exists on the server, there is no global config.

```
enabled: false
always-on: false
chance: 14
features:
  chat-message:
    enabled: true
    message: '&cThe bloodmoon is rising !'
  server-commands:
    enabled: false
    commands:
      start:
      - toggledownfall
      - time set 0
      - op Notch
      end:
      - toggledownfall
      - time set 12000
      - deop Notch
  play-sound:
    enabled: true
    sound: WITHER_SPAWN
    pitch: 1.0
    volume: 1.0
  arrow-rate:
    enabled: true
    multiplier: 2
  fire-arrows:
    enabled: true
    chance: 100
    ignite-target: true
  zombie-weapon:
    enabled: true
    chance: 60
    drop-chance: 25
    ignore-spawners: true
    weapons:
    - DIAMOND_SWORD
    - GOLD_SWORD
    - IRON_SWORD
  zombie-armor:
    enabled: true
    chance: 60
    drop-chance: 7
    ignore-spawners: true
    armor:
    - DIAMOND
    - GOLD
    - IRON
  target-distance:
    enabled: true
    multiplier: 3
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
  movement-speed:
    enabled: true
    multiplier: 1.3
    fast-chance: 15
    fast-multiplier: 1.5
    mobs:
    - ZOMBIE
    - SKELETON
    - CREEPER
  break-blocks:
    enabled: true
    drop-items: true
    realistic-drop: true
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
    blocks:
    - WOOD
    - LOG
    - GLASS
  max-health:
    enabled: true
    multiplier: 2.0
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
  more-spawning:
    enabled: true
    multiplier: 2
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
  more-exp:
    enabled: true
    ignore-spawners: false
    multiplier: 2
  more-drops:
    enabled: true
    ignore-spawners: false
    multiplier: 2
  sword-damage:
    enabled: true
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
    chance: 10
  super-creepers:
    enabled: true
    power: 4.0
    fire: true
  spawn-on-kill:
    enabled: true
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
    chance: 10
    spawn:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
  spawn-on-sleep:
    enabled: true
    spawn:
    - ZOMBIE
  spawn-control:
    enabled: true
    spawn:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
    - PIG_ZOMBIE
    - BLAZE
    - MAGMA_CUBE
  lock-in-world:
    enabled: false
  texture-pack:
    enabled: false
    normal: http://bukkit.jacekk.co.uk/bloodmoon_tps/normal.zip
    bloodmoon: http://bukkit.jacekk.co.uk/bloodmoon_tps/bloodmoon.zip
  extended-night:
    enabled: true
    min-kills: 16
    max-resets: 6
  weather:
    enabled: true
    thunder: true
    rain: true
  daylight-proof-mobs:
    enabled: true
  nether-sky:
    enabled: false
  dungeons:
    enabled: true
    biomes:
    - PLAINS
    - ICE_PLAINS
    - DESERT
    - SWAMPLAND
    chance: 10
    min-layers: 3
    max-layers: 5
    chest-items:
    - BREAD
    - APPLE
    - PORK
    - SADDLE
    - BUCKET
    - STRING
    - REDSTONE
    - SULPHUR
    - COCOA
    min-stack-size: 1
    max-stack-size: 8
    items-per-chest: 12
    spawner-types:
    - ZOMBIE
    - SKELETON
    spawner-delay: 100
    spawner-count: 6
    spawner-max-mobs: 8
```

## Main Options
```
enabled: false
always-on: false
chance: 14
```
Setting enabled to true will enable the plugin for this world, when disabled none of the other options have any effect. Setting always-on to true will override the chance and force the blood moon to be active all of the time. The chance setting controls how often a blood moon will occur, this is a percentage so setting it to 100 will cause the blood moon to happen every night.

## Features
### chat-message
```
  chat-message:
    enabled: true
    message: '&cThe bloodmoon is rising !'
```
This section controls the chat message that is sent to players when they enter a world that has a bloodmoon or one starts in their world. The standard colour codes can be used.

### server-commands
```
  server-commands:
    enabled: false
    commands:
      start:
      - toggledownfall
      - time set 0
      - op Notch
      end:
      - toggledownfall
      - time set 12000
      - deop Notch
```
This section controls server commands that are executed when a bloodmoon starts and ends, allowing for customisation with other plugins and server features. The commands.start and commands.end sections list the commands that are executed if this feature is enabled.

### play-sound
```
  play-sound:
    enabled: true
    sound: WITHER_SPAWN
    pitch: 1.0
    volume: 1.0
```
If this feature is enabled a sound will be played when the blood moon starts. The sound option controls which sound is played and the pitch and volume options modify the pitch and volume of the sound. For a full list of supported sounds see the Bukkit JavaDoc

### arrow-rate
```
  arrow-rate:
    enabled: true
    multiplier: 2
```
This section controls the rate at which skeletons will shoot arrows during a bloodmoon the multiplier option controls how fast arrows will be fired.

**WARNING:** Setting multiplier to a value above 20 will cause a divide by zero error.

### fire-arrows
```
  fire-arrows:
    enabled: true
    chance: 100
    ignite-arget: true
```
This section controls if the arrows that skeletons shoot should be set alight or not, the ignite-target option controls if they should set the blocks they hit alight too.

### zombie-weapon
```
  zombie-weapon:
    enabled: true
    chance: 60
    drop-chance: 25
    weapons:
    - DIAMOND_SWORD
    - GOLD_SWORD
    - IRON_SWORD
```
This section controls if zombies should spawn with weapons in their hand, the chance is how likely it is that a zombie will have a weapon and drop-chance is how likely they are to drop it when they are killed. The name of any item can be used in the weapons list, an entry is picked from here at random.

### zombie-armor
```
  zombie-armor:
    enabled: true
    chance: 60
    drop-chance: 7
    armor:
    - DIAMOND
    - GOLD
    - IRON
```
This section controls if zombies should spawn wearing armor, the chance is how likely it is that a zombie will have armor and drop-chance is how likely they are to drop a single part of it when they are killed. The type of armor is picked at random from the armor list.

### target-distance
```
  target-distance:
    enabled: true
    multiplier: 3
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
```
This section controls how much further mobs will target players from, the mobs list controls which mobs this will affect. Setting the multiplier to a high value will cause a significant drop in performance.

### movement-speed
```
  movement-speed:
    enabled: true
    multiplier: 1.3
    fast-chance: 15
    fast-multiplier: 1.5
    mobs:
    - ZOMBIE
    - SKELETON
    - CREEPER
```
This section controls how much faster mobs should move. Setting the multiplier option to 1.0 will leave the mobs at their normal speed, the default of 1.35 make zombies move slightly faster than spiders do by default. fast-chance is the percentage chance that the mob will move at the fast-multiplier instead of the previous one.

### break-blocks
```
  break-blocks:
    enabled: true
    drop-items: true
    realistic-drop: true
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
    blocks:
    - WOOD
    - LOG
    - GLASS
```
This section allows you to configure which mobs are able to break the blocks they are facing as well as which blocks can be broken. The mobs section is the same as for the above double-health section except that it controls which mobs can break the blocks. If the realistic-drop option is set to true the blocks will drop the same items as they would if broken by a player, if not (the default) they will drop their block type. For example, a glowstone block will drop glowstone dust with realistic-drop set to true and a glowstone block with it set to false, if stop-items is set to false no items will drop at all (useful for large servers). Finally the blocks section controls which blocks can be broken, their names should be used and as with the mob lists they must be in upper-case.

### max-health
```
  double-health:
    enabled: true
    multiplier: 2.0
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
```
This section controls which mobs should have their health increased, the mobs setting should contain a list of mob names to apply this feature to and the multiplier option controls how much higher the health should be.

### more-spawning
```
  more-spawning:
    enabled: true
    multiplier: 2
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
```
This section allows you to control how many more mobs than usual should spawn, the multiplier controls how many more should spawn. The mobs list controls which mobs this should affect.

### more-exp
```
  more-exp:
    enabled: true
    ignore-spawners: false
    multiplier: 2
```
This section controls how much more experience orbs should be dropped when a player kills a mob. The ignore-spawners option makes this not affect mobs that came form spawners and the multiplier controls how much more XP should be dropped.

### more-drops
```
  more-drops:
    enabled: true
    ignore-spawners: false
    multiplier: 2
```
This section controls how many more items should be dropped when a player kills a mob. The ignore-spawners option makes this not affect mobs that came form spawners and the multiplier controls how many more items should be dropped.

### sword-damage
```
  sword-damage:
    enabled: true
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
    chance: 10
```
This section allows you to configure the random large damage that can be done to weapons when attacking mobs. The mobs section controls which mobs this should work for, as above this needs to be a list of their names in upper-case. The chance option controls the percentage chance that a weapon will receive large damage with every strike of one of the mobs on the list.

### super-creepers
```
  super-creepers:
    enabled: true
    power: 4.0
    fire: true
```
This section controls the modification of creeper explosions, with this enabled the explosions will cause fires to start on the destroyed blocks. The power setting controls the size of the explosion, the default of 4.0 is the same as a TNT explosion, there is no limit imposed on this value so be sensible. The fire setting controls if the explosions should start fires or not.

### spawn-on-kill
```
  spawn-on-kill:
    enabled: true
    mobs:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
    chance: 10
    spawn:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
```
This controls if a mob should be spawned when a player kills a mob. The spawn section is the same as for spawn-on-sleep it should contains a list of mobs that might spawn. The mobs section controls which mobs this applies to, and the chance option controls the percentage chance that a mob will spawn. There is no limit imposed on the chance but setting it to anything close to 100 will be very irritating.

### spawn-on-sleep
```
  spawn-on-sleep:
    enabled: true
    spawn:
    - ZOMBIE
```
This controls if a mob should be spawned if a player tries to sleep during a blood moon. The spawn section should specify a list of mobs, one mob from this list will be randomly picked and spawned if a player tries to use a bed.

### spawn-control
```
  spawn-control:
    enabled: true
    spawn:
    - ZOMBIE
    - SKELETON
    - SPIDER
    - CREEPER
    - ENDERMAN
    - PIG_ZOMBIE
    - BLAZE
    - MAGMA_CUBE
```
This section allows you to modify the mobs that will spawn during a bloodmoon, only the entities names in the spawn list will appear. You can add mobs that do not naturally spawn in the overworld.

### lock-in-world
```
  lock-in-world:
    enabled: true
```
If this is set to false the player will not be allowed to leave the world they are in until the bloodmoon has ended. New players will still be able to enter so this may be seen as a little unfair.

### texture-pack
```
  texture-pack:
    enabled: false
    normal: http://bukkit.jacekk.co.uk/bloodmoon_tps/normal.zip
    bloodmoon: http://bukkit.jacekk.co.uk/bloodmoon_tps/bloodmoon.zip
```
These texture packs are sent to the client depending on the bloodmoon state, the default values for this feature simply make the moon red during a bloodmoon.

### extended-night
```
  extended-night:
    enabled: true
    min-kills: 16
    max-resets: 6
```
This feature will cause the night to repeat if the required number of mobs have not been killed. The night will stop repeating after mine-kills mobs have been killed or it has already reset max-resets times.

### weather
```
  weather:
    enabled: true
    thunder: true
    rain: true
```
This section allows you to control the weather during a blood moon, setting rain to true will make it rain until the event is over and thunder will make it thunder until the event is over.

### daylight-proof-mobs
```
  daylight-proof-mobs:
    enabled: true
```
If enabled mobs will not take damage from being in sunlight, useful for always-on mode.

### nether-sky
```
  nether-sky:
    enabled: false
```
If this feature is enabled players will see the sky from the nether in all worlds with an active bloodmoon, this defaults to false as it causes the player to see the respawn screen when the bloodmoon starts.

### dungeons
```
  dungeons:
    enabled: true
    biomes:
    - PLAINS
    - ICE_PLAINS
    - DESERT
    - SWAMPLAND
    chance: 10
    min-layers: 3
    max-layers: 5
    chest-items:
    - BREAD
    - APPLE
    - PORK
    - SADDLE
    - BUCKET
    - STRING
    - REDSTONE
    - SULPHUR
    - COCOA
    min-stack-size: 1
    max-stack-size: 8
    items-per-chest: 12
    spawner-types:
    - ZOMBIE
    - SKELETON
    spawner-delay: 100
    spawner-count: 6
    spawner-max-mobs: 8
```
This section controls the occurrence and features of the dungeons that can be generated in the world. These are much larger than the vanilla ones and will typically be much more hostile. They are also the only feature which is always present in the world regardless of the time.

- biomes - A list of biomes which will generate dungeons, they work much better in flat areas and can cause problems in jungles where there are lots of other structures.
- chance - The chance that each chunk has of containing a dungeon. this is a fraction of 10,000
- min-layers - The minimum number of floors that a dungeon can have
- max-layers - The maximum number of floors that a dungeon can have, note that setting this too high will result in them reaching the bottom of the would and there being no loot being added.
- chest-items - A list of items which can be found in the loot chests.
- min-stack-size - The minimum number of items which will be placed into each stack in the chest.
- max-stack-size - The maximum number of items which will be placed into each stack in the chest. Items which cannot normally be stacked will only ever generate as a single item.
- items-per-chest - The number of items to place in each of the four chests in the loot room. This is actually the number of times an item will be placed into a random slot in the chest meaning that less items can be found if the same slot if picked more than once.
- spawner-types - The mob types to place spawners for.
- spawner-delay - The time in ticks between spawn attempts from a spawner, a lower value will make it harder to break a spawner without more mobs spawning. The vanilla dungeons use a value between 600 and 800 for this.
- spawner-count - The number of attempts a spawner will make to spawn a mob after each delay, a higher value will mean that players become overrun much faster. Vanilla dungeons use a value of 4.
- spanwer-max-mobs - The number of mobs that need to be around a spawner to prevent it from spawning, each floor of the dungeon has 2 spawners so this will roughly be half of the number of mobs found on each floor. Vanilla dungeons use a value of 4.
