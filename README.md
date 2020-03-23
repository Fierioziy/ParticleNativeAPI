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

# Dependency used
- ObjectWeb's ASM library.

# Resource
Plugin can be downloaded from the Spigot repository (soon).

# How to use
### Include plugin jar as dependency in your Eclipse/IntelliJ project.
Plugin's jar contains classes and javadoc commented source code
files which your IDE should automatically detect to display javadoc's hints.

### Initial setup
Include this plugin as dependency or soft-dependency in your `plugin.yml` file.
```yaml
depend: [ParticleNativeAPI]
```

Obtain `ParticleNativeAPI` plugin instance.
```java
ParticleNativeAPI api = ParticleNativeAPI.getInstance();
if (api == null) {
    // error, plugin not present
}
```

It is nice to check if API has been properly generated and react to it properly.
Otherwise, you might get `IllegalStateException` on any API access if something
went wrong (for ex. Minecraft changed packet constructor).
```java
if (!api.isValid()) {
    // handle error here ...
}
```

### ServerConnection and PlayerConnection
Get `ServerConnection` instance used to send packets to players and cache it somewhere.
You can also obtain `PacketConnection` wrapper from `ServerConnection` to cache player's NMS `PlayerConnection`. 
```java
// cache somewhere ServerConnection
ServerConnection serverConn = api.getServerConnection();

Player somePlayer = ...

// you can get PlayerConnection from ServerConnection
PlayerConnection somePlayerConn = serverConn.getPlayerConnection(somePlayer);
```

Both, `ServerConnection` and `PlayerConnection` accept ***any valid*** Minecraft Packet you pass to them.
You can create other packets using Reflection or other APIs and pass those to them.

```java
Object someReflectedPacket = ...;
serverConn.sendPacket(somePlayer, someReflectedPacket);
```

### Using particles lists
Get desired particle list you would like to use and cache it somewhere.
```java
// cache it for easier use
Particles_1_8 particles_1_8 = api.getParticles_1_8();

// or use other particles list
Particles_1_13 particles_1_13 = api.getParticles_1_13();
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
Create NMS particle packet object using one of particles lists. Basic particles have tons of overload methods
to easily construct packet.
```java
Object somePacket1 = particles_1_8.FLAME().create(true, somePlayer.getLocation());

// some particles can be accesses from other particle lists
// even those, which had name changed
Object somePacket2 = particles_1_13.FLAME().create(true, somePlayer.getLocation();

// send packet using ServerConnection
serverConn.sendPacket(somePlayer, somePacket1);

// ... or use PlayerConnection
playerConn.sendPacket(somePacket2);

// you can use much more detailed packet constructor with full control over parameters
// or if some parameters MUST BE validated (for ex. in NOTE particle), javadoc comment
// will tell you about it
Location loc = somePlayer.getLocation();

// create packet with detailed method
Object somePacket3 = particle_1_8.FLAME().create(true, loc.getX(), loc.getY(), loc.getX(),
                             0D, 0D, 0D,
                             0D, 1);
                             
// send it to player
serverConn.sendPacket(somePlayer, somePacket3);
```

### Constructing packets with particle's unique features
Some particles have additional features with extended set of overloaded methods to create packets.
You can determine which particle have additional features by looking
at particle list's interface class. To check the methods for certain's particle type, look
at class it extends.
```java
// you can store particle types in variables
ParticleTypeDir flameType = particles_1_8.FLAME();
ParticleTypeColorable spellMobType = particle_1_8.SPELL_MOB();
etc ...

// create yellow SPELL_MOB particle
Object packetColored = spellMobType.createColored(true, loc, new Color(255, 255, 0));

// send it to player
serverConn.sendPacket(somePlayer, packetColored);
```

Most of particles with features and just basic particles that has unique behavior
only when certain parameters are met. You can invoke method
with those parameters by yourself if you want.

## Compatibility
Tested Spigot versions: 1.7.10, 1.8.8, 1.12, 1.14.3.

Plugin should be compatible till next major Minecraft implementation change.
