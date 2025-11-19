# Table of content
- [Including API directly to project](#including-api-directly-to-project)
- [Referencing plugin jar as dependency](#referencing-plugin-jar-as-dependency-in-your-eclipseintellij-project)
- [General API structure](#general-api-structure)
- [General usage flow](#general-usage-flow)
- [Particle lists](#particle-lists)
- [Constructing packets with particle's features](#constructing-packets-with-particles-features)
- [Caching returned API objects](#caching-returned-api-objects)

### Including API directly to project
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
            <version>4.5.0</version>
            <scope>compile</scope>
        </dependency>

        <!-- other dependencies -->
    </dependencies>
```

You can alternatively try to directly include content of `ParticleNativeAPI-core-sources.jar`
to Your project (and refactor API package to Your package).
I've tried to structure code in a way that should
make it possible, however, **I do not support doing this**.

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

### Referencing plugin jar as dependency in your Eclipse/IntelliJ project.
Include it as a reference jar, **do not include plugin classes into Your plugin**.

Provided archive contains both the plugin's jar and sources jar
which your IDE should automatically detect
to display javadoc's hints when you provide them to it.

Alternatively you can use Maven (from official Maven repository):
```xml
    <dependencies>
        <dependency>
            <groupId>com.github.fierioziy.particlenativeapi</groupId>
            <artifactId>ParticleNativeAPI-plugin</artifactId>
            <version>4.5.0</version>
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
    // everything is fine, get API
    ParticleNativeAPI api = ParticleNativePlugin.getAPI();
    ...
}
else {
    // handle error
}
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
    ...
}
else {
    // handle plugin absence (and avoid referencing it!)
    // if you try to reference it here, you WILL get NoClassDefFoundError
    // or similar exception
}
```

An `isValid` method is used to check if API has been properly generated.
Otherwise, you might get `ParticleException` on any API access if something
went wrong (for ex. Minecraft changed something internally that API is not ready for).

### General API structure
Entire API basically consists of:
- `ParticleNativeAPI`
    - has particle lists
- `ParticleList_X` (`X` is for ex. `1_8`, `1_13`, ...)
    - have particles that **MAY BE** present, but are non-null
      - so you can use `isPresent()` method to check their presence
        in current server version
    - have naming convention as from `X` version
- `ParticleType` related classes
    - used to make `ParticlePacket` with wrapped packet
    - each of them either has
        - `packet` (or similarly prefixed) methods to create packet
        - or methods to firstly select properties of complex particle
- `ParticlePacket`
    - used to send created packet to players

### General usage flow
General flow is very simple method chain:
- from particle API -> get particle list -> get particle
- select properties of particle
- create packet
- send it to players

Example usage flow for simple particle types:
```java
ParticleNativeAPI particleApi = ParticleNativeCore.loadAPI(this);

...

particleApi.LIST_1_8.CRIT_MAGIC                 // api -> list -> particle
        .packet(true, somePlayer.getLocation()) // create packet
        .sendTo(somePlayer);                    // send it to player
```

Example usage flow for complex particle types:
```java
ParticleNativeAPI particleApi = ParticleNativeCore.loadAPI(this);

...

particleApi.LIST_1_13.DUST                      // api -> list -> particle
        .color(255, 255, 0, 2D)                 // properties: yellow dust with size 2x
        .packet(true, somePlayer.getLocation()) // create packet
        .sendTo(somePlayer);                    // send it to player
```

### Particle lists
They are in `ParticleNativeAPI` fields.

Each list has name in format `ParticleList_X` where `X` is MC version from
which particle naming convention and particle presence is taken.

All particle lists attempt to provide same particle between renames or merges.

They also attempt to provide cross-version compatibility (for ex. usage
of `ENCHANTED_HIT` effect name from `ParticleList_1_13` should work on MC 1.8), however
this is not always possible.

Use `isPresent` method on particle type to handle such cases.

All particle types in lists are guaranteed to be non-null, so
you can safely use `isPresent` method on them before creating packets.

### Constructing and sending particle packets
To construct a particle packet, get desired particle type from chosen particles list.

Basic particles have `packet` method with tons of overloads to easily construct packet.

Before using certain particle type, it is nice to check if it is supported by current server version.
Otherwise, you might get `ParticleException` if you try to use particle that
is not present in current Minecraft version.
```java
if (!particleApi.LIST_1_8.FLAME.isPresent()) {
    // handle particle type absence ...
}
```

To send packets, use `ParticlePacket` returned
by `packet` (or similarly prefixed) methods.
It contains several methods to efficiently send
wrapped packet to target players.

Example usages:
```java
particleApi.LIST_1_8.CRIT_MAGIC
        .packet(true, somePlayer.getLocation())
        .sendTo(somePlayer);

// some particles can be accesses from other particle lists
// even those, which had name changed/merged
particleApi.LIST_1_13.ENCHANTED_HIT
        .packet(true, somePlayer.getLocation())
        .sendTo(somePlayer);

// you can use much more detailed packet constructor with
// full control over parameters
Location loc = somePlayer.getLocation();

particleApi.LIST_1_8.FLAME
        .packet(true, loc.getX(), loc.getY(), loc.getZ(),
                0D, 0D, 0D,
                0D, 1)
        .sendTo(somePlayer);
```

### Constructing packets with particle's features
Some particles have additional features with extended set of method overloads to create packets.

You can determine which particles have additional features by looking
at particles lists interface class (for ex. `ParticleList_1_8` class)
and checking particle's class name.

To check the methods for certain's particle type, look at its class for method
overloads or (if present) class it extends.

Most of them are either `packet` methods (or with such prefix) or
methods selecting particle properties (for ex. color).

There are currently 17 types of particle type in this API:
- `ParticleType`,
- `ParticleTypeBlock`,
- `ParticleTypeBlockMotion`,
- `ParticleTypeColor`,
- `ParticleTypeColorable extends ParticleType`,
- `ParticleTypeDust`,
- `ParticleTypeDustColorTransition`,
- `ParticleTypeItemMotion`,
- `ParticleTypeMotion extends ParticleType`,
- `ParticleTypeNote extends ParticleType`,
- `ParticleTypePowerMotion`,
- `ParticleTypeRedstone extends ParticleType`,
- `ParticleTypeSculkChargeMotion`,
- `ParticleTypeShriek`,
- `ParticleTypeSpell`,
- `ParticleTypeVibration`,
- `ParticleTypeVibrationSingle`.

All particle types that extends `ParticleType` internally only invokes `packet` method with certain parameters.

You can invoke `packet` method with those certain parameters by yourself if you want.

Example usage of each type:
```java
Location loc = ...;
Location loc2 = ...;

// ParticleType
particleApi.LIST_1_8.EXPLOSION
        .packet(true, loc)
        .sendTo(somePlayer);

// ParticleTypeBlock (of diamond block)
particleApi.LIST_1_8.FALLING_DUST
        .of(Material.DIAMOND_BLOCK)
        .packet(true, loc)
        .sendTo(somePlayer);
                             
// ParticleTypeBlockMotion (of diamond block with upward motion)
particleApi.LIST_1_8.BLOCK_CRACK
        .of(Material.DIAMOND_BLOCK)
        .packetMotion(true, loc, 0D, 1D, 0D)
        .sendTo(somePlayer);

// ParticleTypeColor (yellow color with half visibility)
particleApi.LIST_1_19_PART.ENTITY_EFFECT
        .color(Color.fromRGB(255, 255, 0), 125)
        .packet(true, loc)
        .sendTo(somePlayer);

// ParticleTypeColorable (yellow color)
particleApi.LIST_1_8.SPELL_MOB
        .packetColored(true, loc, Color.fromRGB(255, 255, 0))
        .sendTo(somePlayer);

// ParticleTypeDust (yellow dust of size 2x)
particleApi.LIST_1_13.DUST
        .color(Color.fromRGB(255, 255, 0), 2D)
        .packet(true, loc)
        .sendTo(somePlayer);

// ParticleTypeDustColorTransition (yellow dust fading into green of size 2x)
particleApi.LIST_1_13.DUST_COLOR_TRANSITION
        .color(Color.fromRGB(255, 255, 0),
               Color.fromRGB(0, 255, 0), 2D)
        .packet(true, loc)
        .sendTo(somePlayer);

// ParticleTypeItemMotion (of golden apple with upward motion)
particleApi.LIST_1_8.ITEM_CRACK
        .of(Material.GOLDEN_APPLE)
        .packetMotion(true, loc, 0D, 1D, 0D)
        .sendTo(somePlayer);

// ParticleTypeMotion (with upward motion)
particleApi.LIST_1_8.FLAME
        .packetMotion(true, loc, 0D, 1D, 0D)
        .sendTo(somePlayer);
                             
// ParticleTypeNote (with red note)
particleApi.LIST_1_8.NOTE
        .packetNote(true, loc, Color.fromRGB(255, 0, 0))
        .sendTo(somePlayer);
                             
// ParticleTypePowerMotion (with upward motion and power 2)
particleApi.LIST_1_19_PART.DRAGON_BREATH
        .power(2D)
        .packetMotion(true, loc, 0D, 1D, 0D)
        .sendTo(somePlayer);
                             
// ParticleTypeRedstone (yellow color)
particleApi.LIST_1_8.REDSTONE
        .packetColored(true, loc, Color.fromRGB(255, 255, 0))
        .sendTo(somePlayer);

// ParticleTypeSculkChargeMotion (rotated 90 degrees clockwise with upward motion)
particleApi.LIST_1_13.SCULK_CHARGE
        .roll(Math.PI / 2D)
        .packetMotion(true, loc, 0D, 1D, 0D)
        .sendTo(somePlayer);

// ParticleTypeShriek (displayed with 20 ticks delay)
particleApi.LIST_1_13.SHRIEK
        .delay(20)
        .packet(true, loc);

// ParticleTypeSpell (yellow color with power 2)
particleApi.LIST_1_19_PART.INSTANT_EFFECT
        .spell(Color.fromRGB(255, 255, 0), 255, 2D)
        .packet(true, loc);

// ParticleTypeVibrationSingle (flying from loc1 to loc2 in 40 ticks)
particleApi.LIST_1_13.VIBRATION
        .packet(true, loc, loc2, 40)
        .sendTo(somePlayer);

// ParticleTypeVibration (10 particles flying to loc2 in 40 ticks)
particleApi.LIST_1_19_PART.VIBRATION
        .flyingTo(loc2, 40)
        .packet(true, loc, 5D, 5D, 5D, 10)
        .sendTo(somePlayer);
```

### Caching returned API objects
To preserve object allocation as much as possible, every method
with `@Shared` annotation returns **ONE AND THE SAME** instance 
with changed state to be used for method chain flow.

Examples of such methods are `packet` related ones and those
selecting properties in complex particle types.

To make an independent copy of such object, use `detachCopy()` method on them.
It will return a copy, that you can cache and use however you want.

You **MUST** use this method if, for example, you want to create
particle pattern that does not change and could 
just be created once, cached and repeatedly efficiently used.

Or want to cache particle type with selected properties.

Example properly made caching (especially `IMPORTANT` part):
```java
ParticleNativeAPI particleApi = ParticleNativeCore.loadAPI(this);

...

List<ParticlePacket> lineParticlePattern = new ArrayList<>();
for (int i = 0; i < 10; ++i) {
    ParticlePacket detachedPacket = particleApi.LIST_1_8.FLAME
        .packet(false, 0D, i, 0D)
        .detachCopy(); // IMPORTANT

    lineParticlePattern.add(detachedPacket);
}
```