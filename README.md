### Future API concept (for 4.0.0)
In some future I plan to make some more significant changes to the API.
There are some overall minor or bigger ones in API which I've documented
[in this issue](https://github.com/Fierioziy/ParticleNativeAPI/issues/9)
and would like to fix them.

Feel free to give feedback about proposed concepts
if You have some ideas :)

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
sending packets, however **it uses no Reflection** to do so!

Spawning particle is made in 2 easy steps:
- **create particle packet**, using particles lists,
- **send it**, using particles lists.

That's it.

```java
// get API from plugin instance
ParticleNativeAPI api = ParticleNativePlugin.getAPI();

// ... or generate it using core module ("this" is Your plugin instance)
api = ParticleNativeCore.loadAPI(this);

// use particles list (cache it for easier use)
// as an example, Particles_1_8 will be used
Particles_1_8 particles = api.getParticles_1_8();

Player player = ...;
Location loc = player.getLocation();

// create a particle packet
Object packet = particles.FLAME().packet(true, loc);

// send this packet to a player
particles.sendPacket(player, packet);
```

To whoever you want to send this packet or on what conditions is up to You. 

### Dependency used (shaded into jar)
- [ObjectWeb's ASM](https://asm.ow2.io/) library.

# Table of content
- [ParticleNativeAPI](https://github.com/Fierioziy/ParticleNativeAPI#particlenativeapi)
- [Resource](https://github.com/Fierioziy/ParticleNativeAPI#resource)
- [Minimal usage example overview](https://github.com/Fierioziy/ParticleNativeAPI#minimal-usage-example-overview)
- [How to use](https://github.com/Fierioziy/ParticleNativeAPI#how-to-use)
  - [Including plugin jar as dependency](https://github.com/Fierioziy/ParticleNativeAPI#including-plugin-jar-as-dependency-in-your-eclipseintellij-project)
  - [Including core API classes directly to project](https://github.com/Fierioziy/ParticleNativeAPI#including-core-api-classes-directly-to-project)
  - [Particles lists and PlayerConnection](https://github.com/Fierioziy/ParticleNativeAPI#particles-lists-and-playerconnection)
  - [Using particles lists](https://github.com/Fierioziy/ParticleNativeAPI#using-particles-lists)
  - [Constructing packets](https://github.com/Fierioziy/ParticleNativeAPI#constructing-packets)
  - [Constructing packets with particle's features](https://github.com/Fierioziy/ParticleNativeAPI#constructing-packets-with-particles-features)
- [Compatibility](https://github.com/Fierioziy/ParticleNativeAPI#compatibility)

# Resource
Plugin can be downloaded:
- from the Spigot repository [here](https://www.spigotmc.org/resources/particlenativeapi-1-7.76480/),
- from the Bukkit repository [here](https://dev.bukkit.org/projects/particlenativeapi),
- from the Github release page [here](https://github.com/Fierioziy/ParticleNativeAPI/releases).

# Minimal usage example overview
```java
public class PluginName extends JavaPlugin {

   // cache particles list for later use
   private Particles_1_8 particles;

   @Override
   public void onEnable() {
       // check if everything is fine
       if (!ParticleNativePlugin.isValid()) {
           getLogger().log(Level.SEVERE, "Error occurred while loading dependency.");
           this.setEnabled(false);
           return;
       }
       
       // get API
       ParticleNativeAPI api = ParticleNativePlugin.getAPI();

       // choose particles lists you want to use
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
       particles.sendPacket(loc, 30D, packet);

       return true;
   }
}
```

# How to use
### Including plugin jar as dependency in your Eclipse/IntelliJ project.
Include it as a reference jar, **do not include plugin classes into Your plugin**.
Read below sections about core module if you want to do so.

Plugin's jars contains classes and documented source code
files which your IDE should automatically detect to display javadoc's hints.

Alternatively you can use Maven (from official Maven repository):
```xml
    <dependencies>
        <dependency>
            <groupId>com.github.fierioziy.particlenativeapi</groupId>
            <artifactId>ParticleNativeAPI-plugin</artifactId>
            <version>3.3.1</version>
            <scope>provided</scope>
        </dependency>

        <!-- other dependencies -->
    </dependencies>
```

Include this plugin as dependency in your `plugin.yml` file.
```yaml
depend: [ParticleNativeAPI]
```

... or soft-dependency:
```yaml
soft-depend: [ParticleNativeAPI]
```

If you use it as dependency, use `isValid` method 
to check if API has been properly generated.
```java
if (!ParticleNativePlugin.isValid()) {
    // handle error
}

// everything is fine, get API
ParticleNativeAPI api = ParticleNativePlugin.getAPI();
```

To use it as soft-dependency, you have to obtain a plugin in a little other way:
```java
Plugin plugin = this.getServer().getPluginManager().getPlugin("ParticleNativeAPI");
if (plugin != null) {
    // you can safely check ParticleNativePlugin plugin
    if (!ParticleNativePlugin.isValid()) {
        // handle error
    }
    
    // everything is fine, get API
    ParticleNativeAPI api = ParticleNativePlugin.getAPI();
}
else {
    // handle plugin absence (and avoid referencing it!) ...
}
```

An `isValid` method is used to check if API has been properly generated.
Otherwise, you might get `ParticleException` on any API access if something
went wrong (for ex. Minecraft changed packet constructor).

### Including core API classes directly to project
You can directly include content of `ParticleNativeAPI-core-sources.jar` to Your project (and refactor API
package to Your package). I've tried to structure code in a way
that should make it possible, however, I do not support doing this.

Use Maven (with shade plugin) to seamlessly include core API (from official Maven repository).

Example including and shading:
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.3</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>

                        <configuration>
                            <!-- replace shaded version with main artifact -->
                            <shadedArtifactAttached>false</shadedArtifactAttached>

                            <!-- relocate API classes to avoid same-classpath-conflicts -->
                            <!-- with other plugins using this core API -->
                            <relocations>
                                <relocation>
                                    <pattern>com.github.fierioziy.particlenativeapi</pattern>
                                    <shadedPattern>me.yourpluginpackage.particleapi</shadedPattern>
                                </relocation>
                            </relocations>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            
            <!--  other plugins ... -->
        </plugins>
        
        <!-- other build config ... -->
    </build>
    <dependencies>
        <dependency>
            <groupId>com.github.fierioziy.particlenativeapi</groupId>
            <artifactId>ParticleNativeAPI-core</artifactId>
            <version>3.3.1</version>
            <scope>compile</scope>
        </dependency>

        <!-- other dependencies -->
    </dependencies>
```

Use `loadAPI` method to generate API using Your plugin's instance:
```java
try {
    ParticleNativeAPI api = ParticleNativeCore.loadAPI(this);
} catch (ParticleException e) {// optional runtime exception catch
    // handle error
}
```

You can use this instance as you would normally use API using plugin reference.

Note that `loadAPI` IS NOT a simple getter (like `getAPI` 
from `ParticleNativePlugin` is).

**It generates API classes implementation on a call.**
It is better to load it once, when Your plugin loads.

### Particles lists and PlayerConnection
Get desired particles list from `ParticleNativeAPI` you would like to use and cache it somewhere.

It is used to create and send particle packets to players.

You can also obtain `PlayerConnection` wrapper from any particles list to
cache an individual player's NMS `PlayerConnection`. 
```java
// example particles list
Particles_1_8 particles_1_8 = api.getParticles_1_8();

// obtaining individual player's PlayerConnection
Player somePlayer = ...;
PlayerConnection somePlayerConn = particles_1_8.createPlayerConnection(somePlayer);
```

Both, particle lists and `PlayerConnection` accept ***any valid*** Minecraft Packet you pass to them.
This plugin provides API to create particle packets, but you can create
other types of Minecraft packets using Reflection or using other APIs to create them.

```java
Object someReflectedPacket = ...;
particles_1_8.sendPacket(somePlayer, someReflectedPacket);

// or using PlayerConnection
somePlayerConn.sendPacket(someReflectedPacket);
```

### Using particles lists
All particle lists attempt to provide same particle types between renames or merges.

They also attempt to provide cross-version compatibility (for ex. usage
of `ENCHANTED_HIT` effect name from `Particles_1_13` should work on MC 1.8), however
this is not always possible.

Use `isPresent` method on particle type to handle such cases.

All particle types in lists are guaranteed to be non-null, so
you can safely use `isPresent` method on them before creating packets.

Most of the time you need to use only one of lists, however you can
freely and safely use both of them. 
```java
Particles_1_8 particles_1_8 = api.getParticles_1_8();
Particles_1_13 particles_1_13 = api.getParticles_1_13();
// future lists ...
```

Getting certain particle type is easy:
```java
// getting FLAME particle type from Particles_1_8
particles_1_8.FLAME();
```

### Constructing packets
To construct a particle packet, get desired particle type from chosen particles list.
Basic particles have `packet` method with tons of overloads to easily construct packet.

All of those methods return packet as `Object` type, because
we can't reference NMS `Packet` object directly.

Before using certain particle type, it is nice to check if it is supported by current server version.
Otherwise, you might get `ParticleException` if you try to use particle that
is not present in current Minecraft version.
```java
if (!particles_1_8.FLAME().isPresent()) {
    // handle particle type absence ...
}
```

**Note: any `packet` method only constructs packet object, it does not send it!**

To send packets, used `sendPacket` related methods.

```java
Object somePacket1 = particles_1_8.CRIT_MAGIC().packet(true, somePlayer.getLocation());

// some particles can be accesses from other particle lists
// even those, which had name changed/merged
Object somePacket2 = particles_1_13.ENCHANTED_HIT().packet(true, somePlayer.getLocation();

// send packet using particles lists
particles_1_8.sendPacket(somePlayer, somePacket1);

// ... or use PlayerConnection (made from previous example)
somePlayerConn.sendPacket(somePacket2);

// you can use much more detailed packet constructor with
// full control over parameters
Location loc = somePlayer.getLocation();

Object somePacket3 = particle_1_8.FLAME().packet(true,
                             loc.getX(), loc.getY(), loc.getZ(),
                             0D, 0D, 0D,
                             0D, 1);
                             
// send it to player
particles_1_8.sendPacket(somePlayer, somePacket3);
```

### Constructing packets with particle's features
Some particles have additional features with extended set of method overloads to create packets.

You can determine which particles have additional features by looking
at particles lists interface class (for ex. `Particles_1_8` class).

To check the methods for certain's particle type, look at its class for method
overloads or (if present) class it extends.

There are currently 13 types of particle type in this API:
- `ParticleType`,
- `ParticleTypeBlock`,
- `ParticleTypeBlockMotion`,
- `ParticleTypeColorable extends ParticleType`,
- `ParticleTypeMotion extends ParticleType`,
- `ParticleTypeDust`,
- `ParticleTypeDustColorTransition`,
- `ParticleTypeItemMotion`,
- `ParticleTypeNote extends ParticleType`,
- `ParticleTypeRedstone extends ParticleType`,
- `ParticleTypeVibration`,
- `ParticleTypeSculkChargeMotion`,
- `ParticleTypeShriek`.

All particle types that extends `ParticleType` only invokes `packet` method with certain parameters.

You can invoke `packet` method with those certain parameters by yourself if you want.

Example usage of each type:
```java
Location loc = ...;
Location loc2 = ...;

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

// ParticleTypeDustColorTransition (yellow dust fading into green of size 2x)
Object packetDustColorTransition = particles_1_13.DUST_COLOR_TRANSITION()
                             .color(new Color(255, 255, 0),
                                    new Color(0, 255, 0), 2D)// this return object can be cached in variable
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

// ParticleTypeVibration (flying from loc1 to loc2 in 40 ticks)
Object packetVibration = particles_1_13.VIBRATION()
                             .packet(true, loc, loc2, 40);

// ParticleTypeSculkChargeMotion (rotated 90 degrees clockwise with upward motion)
Object packetSculkChargeMotion = particles_1_13.SCULK_CHARGE()
                             .roll(Math.PI / 2D)
                             .packetMotion(true, loc, 0D, 1D, 0D);

// ParticleTypeShriek (displayed with 20 ticks delay)
Object packetShriek = particles_1_13.SHRIEK()
                             .delay(20)
                             .packet(true, loc);

// send one of those packets to player
particles_1_8.sendPacket(somePlayer, packet);
```

## Compatibility
Tested Spigot versions: 1.7.10, 1.8.8, 1.12, 1.14.3, 1.15.2, 1.16.1, 1.17, 1.18, 1.19.

It should work on Bukkit (CraftBukkit) as well.

Plugin should be compatible at least between MC 1.7 and MC 1.19 for now.
It will only needs update if new feature/bugfix were added or there were Minecraft changes in packet handling in future versions.

Keep in mind, that **this API will favor backward compatibility
with supported MC versions range instead of forward compatibility of itself**.

It means, that future API updates might break forward compatibility with older versions
of itself just to remain backward compatibility with supported MC versions.

That said, next API updates might sometimes force some changes
in Your code to again be compatible with newer API version.