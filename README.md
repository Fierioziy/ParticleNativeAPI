# ParticleNativeAPI
[![releases](https://img.shields.io/github/v/release/fierioziy/particlenativeapi)](https://github.com/Fierioziy/ParticleNativeAPI/releases)
![](https://img.shields.io/github/release-date/fierioziy/particlenativeapi)
![](https://img.shields.io/github/license/fierioziy/particlenativeapi)
![](https://img.shields.io/github/issues/fierioziy/particlenativeapi)

ParticleNativeAPI is a particle spawning API for Spigot server designed to be:
- fast (comparable to native Java written code!),
- relatively easy and convenient to use,
- cross-version compatible down to MC 1.7,
- flexible with changes in Minecraft available particle list (including removed particles!).

On top of that, this particle API supports spawning certain particles:
- of blocks,
- of items,
- with color (only 1 particle per packet),
- with color and size,
- with certain motion (only 1 particle per packet)

... and still be fast and cross version compatible between Minecraft updates.

Entire API structure was designed to be as lenient as possible with major changes in particle packet class and particle implementation overall.

Spawning particle is made in 2 easy steps:
- **create particle packet**, using one of particle lists,
- **send it**, using either `ServerConnection` or `PlayerConnection`.

That's it.

```java
        // get plugin instance
        ParticleNativeAPI api = ParticleNativeAPI.getPlugin();

        // use its API object (cache them for easier use)
        Particles_1_8 particles = api.getParticles_1_8();
        ServerConnection serverConn = api.getServerConnection();

        Player player = ...;
        Location loc = player.getLocation();

        // create a particle packet
        Object packet = particles.FLAME().packet(true, loc);

        // send this packet to a player
        serverConn.sendPacket(player, packet);
```

To whoever you want to send this packet or on what conditions is up to You. 

### Dependency used (shaded into jar)
- [ObjectWeb's ASM](https://asm.ow2.io/) library.

# Resource
Plugin can be downloaded:
- from the Spigot repository [here](https://www.spigotmc.org/resources/particlenativeapi-1-7.76480/),
- from the Bukkit repository [here](https://dev.bukkit.org/projects/particlenativeapi),
- from the Github release page [here](https://github.com/Fierioziy/ParticleNativeAPI/releases).

# Minimal usage example overview
```java
public class PluginName extends JavaPlugin {

   // cache ServerConnection for later use
   private ServerConnection serverConn;

   // cache particle list for later use
   private Particles_1_8 particles;

   @Override
   public void onEnable() {
       // get API's instance
       ParticleNativeAPI api = ParticleNativeAPI.getPlugin();

       // check if everything is fine
       if (!api.isValid()) {
           getLogger().log(Level.SEVERE, "Error occured while loading dependency.");
           this.setEnabled(false);
           return;
       }

       // cache api objects
       serverConn = api.getServerConnection();
       particles = api.getParticles_1_8();
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

       // create a particle packet
       Object packet = particles.FLAME().packet(true, loc);

       // send this packet to all players within 30 blocks
       serverConn.sendPacket(loc, 30D, packet);

       return true;
   }
}
```

# How to use
### Include plugin jar as dependency in your Eclipse/IntelliJ project.
Include it as a reference jar, **do not include plugin's classes into Your plugin**.

Plugin's jar contains classes and documented source code
files which your IDE should automatically detect to display javadoc's hints.

To import it using Maven, you can use [jitpack.io](https://jitpack.io) repository:
```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>

        <!-- other repositories -->
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>com.github.Fierioziy</groupId>
            <artifactId>ParticleNativeAPI</artifactId>
            <version>1.3.0</version>
            <scope>provided</scope>
        </dependency>

        <!-- other dependencies -->
    </dependencies>
```

### Initial setup
Include this plugin as dependency in your `plugin.yml` file.
```yaml
depend: [ParticleNativeAPI]
```

... or soft-dependency:
```yaml
soft-depend: [ParticleNativeAPI]
```

If you use it as dependency, obtain `ParticleNativeAPI` plugin instance.
```java
ParticleNativeAPI api = ParticleNativeAPI.getPlugin();
if (!api.isValid()) {
    // handle error
}
```

To use it as soft-dependency, you have to obtain plugin in a little other way:
```java
Plugin plugin = this.getServer().getPluginManager().getPlugin("ParticleNativeAPI");
if (plugin != null) {
    // you can safely cast plugin to ParticleNativeAPI plugin
    ParticleNativeAPI api = (ParticleNativeAPI) plugin;
    
    // ... or just directly access static getter
    api = ParticleNativeAPI.getPlugin();

    // of course, check if API successfully loaded
    if (!api.isValid()) {
        // handle error
    }
}
else {
    // handle plugin absence (and avoid referencing it)...
}
```

An `isValid` method is used to check if API has been properly generated.
Otherwise, you might get `IllegalStateException` on any API access if something
went wrong (for ex. Minecraft changed packet constructor).

### ServerConnection and PlayerConnection
Get `ServerConnection` instance used to send packets to players and cache it somewhere.

You can also obtain `PlayerConnection` wrapper from `ServerConnection` to
cache an individual player's NMS `PlayerConnection`. 
```java
// cache somewhere ServerConnection for later use
ServerConnection serverConn = api.getServerConnection();

// obtaining individual player's PlayerConnection
Player somePlayer = ...;
PlayerConnection somePlayerConn = serverConn.getPlayerConnection(somePlayer);
```

Both, `ServerConnection` and `PlayerConnection` accept ***any valid*** Minecraft Packet you pass to them.
This plugin provides API to create particle packets, but you can create
other types of Minecraft packets using Reflection or using other APIs to create them.

```java
Object someReflectedPacket = ...;
serverConn.sendPacket(somePlayer, someReflectedPacket);

// or using PlayerConnection
playerConn.sendPacket(someReflectedPacket);
```

### Using particles lists
Get desired particle list from `ParticleNativeAPI` you would like to use and cache it somewhere.

All particle lists attempt to provide same particle types even if particle
name was changed or merged with other particle.

All particle lists attempt to provide cross-version compatibility (for ex. usage
of `ENCHANTED_HIT` effect name from `Particles_1_13` should work on MC 1.8).

Most of the time you need to use only one of lists.
```java
Particles_1_8 particles_1_8 = api.getParticles_1_8();
Particles_1_13 particles_1_13 = api.getParticles_1_13();
// future lists ...
```

Before using certain particle type, it is nice to check if it is supported by current server version.
Otherwise, you might get `IllegalStateException` if that particle
is not present in current Minecraft version.
```java
if (!particles_1_8.FLAME().isValid()) {
    // handle error
}
```

### Constructing packets
To construct a NMS particle packet object, use one of particles lists. Basic particles
have `packet` method with tons of overloads to easily construct packet.

**Note: `packet` method constructs packet object, it does not send it!**
```java
Object somePacket1 = particles_1_8.CRIT_MAGIC().packet(true, somePlayer.getLocation());

// some particles can be accesses from other particle lists
// even those, which had name changed/merged
Object somePacket2 = particles_1_13.ENCHANTED_HIT().packet(true, somePlayer.getLocation();

// send packet using ServerConnection
serverConn.sendPacket(somePlayer, somePacket1);

// ... or use PlayerConnection
playerConn.sendPacket(somePacket2);

// you can use much more detailed packet constructor with full control over parameters
// and if some parameters will be validated (for ex. in NOTE particle), javadoc comment
// will tell you about it
Location loc = somePlayer.getLocation();

// create packet with detailed method
Object somePacket3 = particle_1_8.FLAME().packet(true,
                             loc.getX(), loc.getY(), loc.getZ(),
                             0D, 0D, 0D,
                             0D, 1);
                             
// send it to player
serverConn.sendPacket(somePlayer, somePacket3);
```

### Constructing packets with particle's unique features
Some particles have additional features with extended set of method overloads to create packets.

You can determine which particle have additional features by looking
at particle list's interface class (for ex. `Particles_1_8` class).

To check the methods for certain's particle type, look at its class for method overloads or (if present) class it extends.

There are currently 9 types of particle type in this API:
- `ParticleType`,
- `ParticleTypeBlock`,
- `ParticleTypeBlockMotion`,
- `ParticleTypeColorable extends ParticleType`,
- `ParticleTypeMotion extends ParticleType`,
- `ParticleTypeDust`,
- `ParticleTypeItemMotion`,
- `ParticleTypeNote extends ParticleType`,
- `ParticleTypeRedstone extends ParticleType`.

All particle types that extends `ParticleType` only invokes `packet` method with certain parameters.

You can invoke `packet` method with those certain parameters by yourself if you want.

Example usage of each type:
```java
Location loc = ...;

// ParticleType
Object packet = particles_1_8.EXPLOSION().packet(true, loc);

// ParticleTypeBlock (of diamond block)
Object packetBlock = particles_1_8.FALLING_DUST()
                             .of(Material.DIAMOND_BLOCK)// this return object can be cached in variable
                             .packet(true, loc);
                             
// ParticleTypeBlockMotion (of diamond block with upward motion)
Object packetBlockMotion = particles_1_8.BLOCK_CRACK()
                             .of(Material.DIAMOND_BLOCK)// this return object can be cached in variable
                             .packetMotion(true, loc, 0D, 1D, 0D);

// ParticleTypeColorable (yellow color)
Object packetColorable = particles_1_8.SPELL_MOB()
                             .packetColored(true, loc, new Color(255, 255, 0));

// ParticleTypeMotion (with upward motion)
Object packetMotion = particles_1_8.FLAME()
                             .packetMotion(true, loc, 0D, 1D, 0D);
                             
// ParticleTypeDust (yellow dust of size 2x)
Object packetDust = particles_1_13.DUST()
                             .color(new Color(255, 255, 0), 2D)// this return object can be cached in variable
                             .packet(true, loc);
                             
// ParticleTypeItemMotion (of golden apple with upward motion)
Object packetItemMotion = particles_1_8.ITEM_CRACK()
                             .of(Material.GOLDEN_APPLE)// this return object can be cached in variable
                             .packetMotion(true, loc, 0D, 1D, 0D);
                             
// ParticleTypeNote (with red note)
Object packetNote = particles_1_8.NOTE()
                             .packetNote(true, loc, new Color(255, 0, 0);
                             
// ParticleTypeRedstone (yellow color)
Object packetRedstone = particles_1_8.REDSTONE()
                             .packetColored(true, loc, new Color(255, 255, 0)); 

// send one of those packets to player
serverConn.sendPacket(somePlayer, packet);
```

## Compatibility
Tested Spigot versions: 1.7.10, 1.8.8, 1.12, 1.14.3, 1.15.2.

It should work on Bukkit (CraftBukkit) as well.

Plugin should be compatible at least between MC 1.7 and MC 1.15 for now.
It will only needs update if new feature/bugfix were added or there were Minecraft changes in packet handling in future versions.
