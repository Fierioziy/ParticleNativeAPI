For version `3.x.x` details of this lib check [Migration from 3.x.x to 4.x.x](#migration-from-3xx-to-4xx)
section.

# ParticleNativeAPI
[![](https://img.shields.io/github/v/release/fierioziy/particlenativeapi)](https://github.com/Fierioziy/ParticleNativeAPI/releases)
[![](https://img.shields.io/github/release-date/fierioziy/particlenativeapi)](https://github.com/Fierioziy/ParticleNativeAPI/releases)
[![](https://img.shields.io/github/issues/fierioziy/particlenativeapi)](https://github.com/Fierioziy/ParticleNativeAPI/issues)

ParticleNativeAPI is a particle spawning API for Spigot and Bukkit server designed to be:
- fast (comparable to native Java written code!),
- cross-version compatible down to MC 1.7 (includes removed particles!),
- flexible in use.
- relatively easy and convenient to use.

On top of that, this particle API supports spawning certain particles:
- of blocks,
- of items,
- with color (only 1 particle per packet),
- with color and size,
- with transition color and size,
- with certain motion (only 1 particle per packet)
- with angle
- with delay

... and still be fast and cross version compatible between Minecraft updates.

Entire API structure targets to reflect how Minecraft handles
sending packets, however it is strongly typed and **uses no Reflection** to do so!

Spawning particle is made in one straightforward method chain flow:
- from API
- get particles list
- get particle (and configure it)
- make particle packet
- send it

That's it. And everything in as compact and efficient way as possible.

```java
// get API from plugin instance
ParticleNativeAPI api = ParticleNativePlugin.getAPI();

// ... or generate it using core module ("this" is Your plugin instance)
particleApi = ParticleNativeCore.loadAPI(this);

Player player = ...;
Location loc = player.getLocation();

// as simple as that
particleApi.LIST_1_8.FLAME  // from desired list get particle
        .packet(true, loc)  // make particle packet
        .sendTo(player);    // send it to certain player(s)
```

To whoever you want to send this packet or on what conditions is up to You. 

### Dependency used (shaded into jar)
- [ObjectWeb's ASM](https://asm.ow2.io/) library.

# Table of content
- [ParticleNativeAPI](#particlenativeapi)
- [Resource](#resource)
- [Minimal usage example overview](#minimal-usage-example-overview)
- [How to use](#how-to-use)
- [Migration from 3.x.x to 4.x.x](#migration-from-3xx-to-4xx)
- [Compatibility](#compatibility)

# Resource
Plugin and other resources can be downloaded:
- from the Spigot repository [here](https://www.spigotmc.org/resources/particlenativeapi-1-7.76480/),
- from the Bukkit repository [here](https://dev.bukkit.org/projects/particlenativeapi),
- from the Github release page [here](https://github.com/Fierioziy/ParticleNativeAPI/releases).

# Minimal usage example overview
```java
public class PluginName extends JavaPlugin {

    // loaded particle API
    private ParticleNativeAPI particleApi;
 
    @Override
    public void onEnable() {
        // load API and store it for later use
        particleApi = ParticleNativeCore.loadAPI(this);

        // or get it from running plugin api instance
        // check if everything is fine with it
      
        // if (ParticelNativePlugin.isValid()) {
        //     particleApi = ParticleNativePlugin.getAPI();
        // }
        // else {
        //     getLogger().log(Level.SEVERE, "Error occurred while loading dependency.");
        //     this.setEnabled(false);
        //     return;
        // }
    }
 
    // example usage
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("somecmd")) return true;
 
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be player to use this command!");
            return true;
        }
 
        Player pSender = (Player) sender;
        Location loc = pSender.getLocation();
 
        // particle spawning
        particleApi.LIST_1_8.FLAME             // select particle from list
                .packet(true, loc)             // create particle packet
                .sendInRadiusTo(player, 30D);  // send it to player if in 30 block radius
 
        return true;
    }
}
```

# How to use
For detailed tutorial about all parts of this API, check
[how-to-use.md](documentation/how-to-use.md) file
from `documentation` folder.

# Implementation details
For detailed implementation details of this API and general tips
about developing it, check
[how-to-use.md](documentation/implementation-details.md) file
from `documentation` folder.

# Migration from 3.x.x to 4.x.x
For migration tips from version 3.x.x to 4.x.x, check
[migration-v3-to-v4.md](documentation/migration-v3-to-v4.md) file
from `documentation` folder.

Master branch for version 3.x.x is `master-v3` and there is
current code for those versions.

Master branch for version 4.x.x is `master`.

If you want to access documentation for version 3.x.x, check `README.md`
from `master-v3` branch.

# Compatibility
Tested Spigot versions: 1.7.10, 1.8.8, 1.12, 1.14.3, 1.15.2, 1.16.1, 
1.17, 1.18, 1.19.3.

It should work on Bukkit (CraftBukkit) as well.

Plugin should be compatible at least between MC 1.7 and MC 1.19.3 for now.
It will only needs update if new feature/bugfix were added
or there were Minecraft changes in packet handling in future versions.

Keep in mind, that **this API will favor compatibility
with supported MC versions range instead of compatibility of itself**.

Most of the time it will be announced as new major
version (for ex. from 3.x.x to 4.x.x)

That said, next API updates might sometimes force some changes
in Your code to again be compatible with newer API version.