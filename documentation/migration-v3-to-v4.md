# Migrating from 3.x.x to 4.x.x version
Many things changed between 3.x.x and 4.x.x versions.

For some short time I'll try to maintain 3.x.x version to give some time
for migration (few months I think).
At least I hope it will be possible because lately
there were many irritating internal MC changes that I barely
made backward compatible (I'm looking at you, `LIGHT` and `VIBRATION` particle). 

# Table of content
- [Important changes to keep in mind](#important-changes-to-keep-in-mind)
- [General tips](#general-tips)
- [`ParticleNativeAPI`](#particlenativeapi)
- [`Particles_X` related classes](#particles_x-related-classes)
- [`ServerConnection` and `PlayerConnection`](#serverconnection-and-playerconnection)
- [`ParticleType` related classes](#particletype-related-classes)

### Important changes to keep in mind
- methods annotated with `@Shared` annotation returns
  **ONE AND THE SAME** instance with changed internal state for memory efficiency
  - to make independent copy of such object, use 
    `detachCopy()` (for example packet caching)

### General tips
- classes in API have been moved around some packages or slightly renamed
  - I think it is better to remove imports and include them again
- general usage flow has changed (for better, no more `Object` NMS Packet)
  - see [how-to-use.md](how-to-use.md) for details about it
  
### `ParticleNativeAPI`
  - it is now abstract class instead of interface
  - now has particle lists in fields instead of getter methods

### `Particles_X` related classes
  - renamed to `ParticleList_X`
  - they are now abstract classes instead of interfaces
  - now have particles in fields instead of getter methods

### `ServerConnection` and `PlayerConnection`
  - they are removed
  - replaced by `ParticlePacket` returned by particle types
  - now there is no way to send *any* NMS `Packet`
    - that previous opportunity was due to how packet sending was structured
  - instead, it is now much easier to send packets
    using `ParticlePacket` in method chain
  - methods sending *in radius* are slightly changed for performance reasons
    - now they accept list of players instead of `Location`
      because packet position is now stored in `ParticlePacket`
    - this was changed because `Location -> getWorld() -> getPlayers()` creates new list 
      on each call which I didn't account for, lol
      
### `ParticleType` related classes
  - they are now interfaces
    - their base implementation is in `core` module and are
      basically the same as previous ones that were in `api` module
  - `packet` methods now return `ParticlePacket` instead of `Object`
    that was literally NMS `Packet`
    - returned object is **ONE AND THE SAME** instance with changed
      wrapped NMS `Packet`
    - (**IMPORTANT**) if you were caching them somewhere,
      call `detachCopy()` on them before storing them somewhere 
      in lists, fields etc.
  - methods selecting particle properties of complex particle type
    now returns **ONE AND THE SAME** instance of particle type
    with changed internal state
      - (**IMPORTANT**) if you were caching them somewhere,
        call `detachCopy()` on them before storing them somewhere in fields etc.
