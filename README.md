# BloodMoon by Hexosse
This is a modified version of [Jacek (betterphp) BloodMoon project](https://github.com/betterphp/BloodMoon)

## Status
Currently working in spigot 1.9

## Description
This plugin will add a blood moon event to your server, each night has a configurable chance of a blood moon. During a blood mood the game is made a lot harder:
- Skeletons shoot faster.
- Skeletons shoot fire arrows (that start fires).
- Zombies spawn with weapons and armor.
- Mobs can break blocks when focused on a player.
- Hostile mobs have more health.
- Small chance of your sword taking massive damage with each strike.
- Creepers have much more powerful explosions (that cause fire).
- Any hostile mob killed has a small chance of coming back as a zombie (or other mob).
- A mob will spawn if you try to sleep.
- More mobs will spawn.
- Mobs will drop more XP when killed.
- Mobs will drop more items when killed
- Mobs will target players from further away
- Mobs will move faster
- Custom texture pack during a bloodmoon, the default one makes the moon red.

All of the features can be configured for each world. See the config section for more information.

## Commands
- /bloodmoon start - Starts a bloodmoon event.
- /bloodmoon stop - Stops a current bloodmoon event.
- /bloodmoon next - Schedules a bloodmoon for the next night.

## Permissions
- bloodmoon.admin.start - Allows the player to manually start a bloodmoon
- bloodmoon.admin.stop - Allows the player to manually stop a bloodmoon
- bloodmoon.admin.ignore-world-lock - Allows the player to leave the world even if the bloodmoon is active and the lock-in-world feature is enabled

All of these default to OPs only.

## Config
Information relating to the config file for this plugin can be found on the config page.

##  Potential Conflicts
Due to limitations of the Bukkit API, this plugin has to override certain methods from the Minecraft source. If any other plugin replaces the same things it will not be compatible with BloodMoon and weird stuff may happen ! You would still be able to use both plugins but you would need to disable the break-blocks option in the config file, even them some features of either plugin may not work properly.