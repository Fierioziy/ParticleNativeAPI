# ParticleNativeAPI
Supporting all particles at once is a very clumsy task considering that particle packet class
changes it's constructor between some Minecraft versions and some particles get added, merged or removed between them.

A main concept of this plugin is to:
- provide constructing particles with runtime speed comparable to statically written Java code,
- maintain cross-version compatibility across Minecraft updates,
- provide various particle lists to support changed particle names or removed ones,
- provide access to NMS/OBC objects using non-reflective wrappers,
- reflect internal structure of how Minecraft sends packets.

This plugin uses Reflection only to properly determine NMS and OBC classes and using this data
to generate API implementation at server startup.

### Dependency used
- ObjectWeb's ASM library.

# Resource
Plugin can be downloaded:
- from the Spigot repository (soon),
- from the Bukkit repository [here](https://dev.bukkit.org/projects/particlenativeapi).

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
        ParticleNativeAPI api = ParticleNativeAPI.getInstance();

        // check if everything is fine
        if (api == null || !api.isValid()) {
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
        Object packet = particles.FLAME().create(true, loc);

        // send this packet to all players within 30 blocks
        for (Player p : loc.getWorld().getPlayers()) {
            if (loc.distanceSquared(p.getLocation()) <= 30D * 30D) {
                serverConn.sendPacket(p, packet);
            }
        }

        return true;
    }
}
```

# How to use
### Include plugin jar as dependency in your Eclipse/IntelliJ project.
Plugin's jar contains classes and documented source code
files which your IDE should automatically detect to display javadoc's hints.

### Initial setup
Include this plugin as dependency or soft-dependency in your `plugin.yml` file.
```yaml
depend: [ParticleNativeAPI]
```

Obtain `ParticleNativeAPI` plugin instance.
```java
ParticleNativeAPI api = ParticleNativeAPI.getInstance();
if (api == null && !api.isValid()) {
    // error, plugin not present or error occured
}
```

An `isValid` method is used to check if API has been properly generated.
Otherwise, you might get `IllegalStateException` on any API access if something
went wrong (for ex. Minecraft changed packet constructor).

### ServerConnection and PlayerConnection
Get `ServerConnection` instance used to send packets to players and cache it somewhere.

You can also obtain `PacketConnection` wrapper from `ServerConnection` to
cache individual player's NMS `PlayerConnection`. 
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
Get desired particle list you would like to use and cache it somewhere.

All particle lists attempt to provide same particle types even if particle
name was changed or merged with other particle.

All particel lists attempt to provide cross-version compatibility (for ex. usage
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
have `create` method with tons of overloads to easily construct packet.

**Note: `create` method constructs packet object, it does not send it!**
```java
// 
Object somePacket1 = particles_1_8.CRIT_MAGIC().create(true, somePlayer.getLocation());

// some particles can be accesses from other particle lists
// even those, which had name changed/merged
Object somePacket2 = particles_1_13.ENCHANTED_HIT().create(true, somePlayer.getLocation();

// send packet using ServerConnection
serverConn.sendPacket(somePlayer, somePacket1);

// ... or use PlayerConnection
playerConn.sendPacket(somePacket2);

// you can use much more detailed packet constructor with full control over parameters
// and if some parameters will be validated (for ex. in NOTE particle), javadoc comment
// will tell you about it
Location loc = somePlayer.getLocation();

// create packet with detailed method
Object somePacket3 = particle_1_8.FLAME().create(true,
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
- `ParticleTypeBlockDir`,
- `ParticleTypeColorable extends ParticleType`,
- `ParticleTypeDir extends ParticleType`,
- `ParticleTypeDust`,
- `ParticleTypeItemDir`,
- `ParticleTypeNote extends ParticleType`,
- `ParticleTypeRedstone extends ParticleType`,

Example usage of each type:
```java
Location loc = ...;

// ParticleType
Object packet = particles_1_8.EXPLOSION().create(true, loc);

// ParticleTypeBlock (of diamond block)
Object packetBlock = particles_1_8.FALLING_DUST()
                             .of(Material.DIAMOND_BLOCK)// this can be cached in variable
                             .create(true, loc);
                             
// ParticleTypeBlockDir (of diamond block with upward motion)
Object packetBlockDir = particles_1_8.BLOCK_CRACK()
                             .of(Material.DIAMOND_BLOCK)// this can be cached in variable
                             .createDir(true, loc, 0D, 1D, 0D);

// ParticleTypeColorable (yellow color)
Object packetColorable = particles_1_8.SPELL_MOB()
                             .createColored(true, loc, new Color(255, 255, 0));

// ParticleTypeDir (with upward motion)
Object packetDir = particles_1_8.FLAME()
                             .createDir(true, loc, 0D, 1D, 0D);
                             
// ParticleTypeDust (yellow dust of size 2x with upward motion)
Object packetDust = particles_1_13.DUST()
                             .color(new Color(255, 255, 0), 2D)// this can be cached in variable
                             .createDir(true, loc, 0D, 1D, 0D);
                             
// ParticleTypeItemDir (of golden apple)
Object packetItemDir = particles_1_8.ITEM_CRACK()
                             .of(Material.GOLDEN_APPLE)// this can be cached in variable
                             .createDir(true, loc, 0D, 1D, 0D);
                             
// ParticleTypeNote (with red note)
Object packetNote = particles_1_8.NOTE()
                             .createNote(true, loc, new Color(255, 0, 0);
                             
// ParticleTypeRedstone (yellow color)
Object packetRedstone = particles_1_8.REDSTONE()
                             .createColored(true, loc, new Color(255, 255, 0)); 

// send one of those packets to player
serverConn.sendPacket(somePlayer, packet);
```

All particle types that extends `ParticleType` only invokes `create` method with certain parameters.

You can invoke this method with those certain parameters by yourself if you want.

## Compatibility
Tested Spigot versions: 1.7.10, 1.8.8, 1.12, 1.14.3.

Plugin should be compatible till next major Minecraft implementation change.
