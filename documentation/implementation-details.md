# Implementation details
Here I prepared some general information about how API
is implemented and structured, if you are sane enough to contribute to this API.

Most implementation is written 
using [ObjectWeb's ASM](https://asm.ow2.io/) library.

It basically allows operating on classes at bytecode level (either 
dynamically creating class bytecode from scratch or manipulating existing ones).

Here I am using this library for creating classes that directly
use NMS/OBC stuff without any Reflection.

I'd rather not explain everything that you should know about ASM, because
this page would be extremely long (ASM itself has
literally ~150 pages long user guide about it), but rather highlight
general tips during development.

# Table of content
- [Project structure](#project-structure)
- [General information about ASM](#general-information-about-asm)
- [General implementation structure](#general-implementation-structure)
- [Conventions in `core.asm` package](#conventions-in-coreasm-package)
- [Package `core.asm`](#package-coreasm)
  - [Package `core.asm.particle`](#package-coreasmparticle)
  - [Package `core.asm.particle.type`](#package-coreasmparticletype)
  - [Package `core.asm.packet`](#package-coreasmpacket)
- [Unit tests](#unit-tests)
- [Manual tests](#manual-tests)

## Project structure
Project consists of modules and separate projects.

Modules are:
- `ParticleNativeAPI-api`
  - contains classes considered as API
- `ParticleNativeAPI-core`
  - contains classes considered as implementation
    that may change a lot
  - the only API class here is `ParticleNativeCore`
- `ParticleNativeAPI-plugin`
  - builds shared jar with implemented `ParticleNativeAPI-core`
    module
  - it is deprecated, better to shade core lib by yourself
    in your plugins (or core plugin)

Separate projects are:
- `ParticleNativeAPI-plugin-test`
  - plugin used to test API
  - it is easier to check if everything is fine
    if lib is used outside maven reactor
- `ParticleNativeAPI-plugin-benchmark`
  - plugin used to benchmark lib in simple way
  - separated project due to version-specific benchmark code
    referencing spigot jar directly

## General information about ASM
So, ASM follows so called *visitor pattern* to manipulate class bytecode.

It basically means, that during *visiting* parts of a class you would
listen for certain part of it and then alter it (add something,
change existing, remove).

The same applies when making bytecode from scratch - you *visit* parts of class
that you want to be present in actual bytecode which is exactly what I am doing
in this API implementation.

During development, there is an extremely useful monstrosity:
- [The Java® Virtual Machine Specification ](https://docs.oracle.com/javase/specs/jvms/se8/html/)
    - an ultimate one-page specification of literally *everything* about JVM

However, the most important chapters of above JVM specification are:
- [Chapter 6. The Java Virtual Machine Instruction Set ](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html)
  - most commonly used by me chapter of above JVM specification
  - it contains instruction set useful for method code writing
- [Chapter 4. The class File Format ](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html)
  - most important parts:
    - 4.2. The Internal Form of Names
    - 4.3. Descriptors
      - especially *4.3.2. Field Descriptors* which contains
        primitive and class descriptors
        
Most of ASM naming conventions are lined up with those specifications, so
you can match up information that you need for ASM.

**VERY IMPORTANT**: An easy mistake can be made when writing method code
when referencing 32-bit or 64-bit local variables.

Double and long primitives (which are 64-bit) takes **TWO SLOTS**
on method stack and **TWO SLOTS** in local variable array.

When loading/storing value in them, you have to offset index by 2 instead of 1.

Example:
```java
MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
        SET_PACKET_METHOD_NAME,
        "(" + refs.packet_1_7.desc() + "DDD)V", null, null);
mv.visitCode();

int local_this = 0;    // object references are 32-bit, take 1 slot
int local_packet = 1;
int local_x = 2;       // x, y, z are double 64-bit primitives, take 2 slots
int local_y = 4;
int local_z = 6;

...

// this.x = x;
mv.visitVarInsn(ALOAD, local_this); // loads "this" object from index 0
mv.visitVarInsn(DLOAD, local_x);    // load double from index 2 AND 3
mv.visitFieldInsn(PUTFIELD, implType.internalName(), "x", "D");

...
```

## General implementation structure
General class structure is as follows:
- `ParticleNativeCore` (and `loadAPI(JavaPlugin)` method)
    - an entry point of everything
- `core.asm` package
    - contains classes related to ASM and class writing
- `core.packet` package
    - contains base classes for packet wrappers
- `core.partice.type` package
    - contains base implementations of particle type interfaces from API
  
It can be categorized as 4 layers (described in below sections):
- API layer
- base implementation layer
  - implementation part that does not need to be in ASM code
- MC non-version-specific ASM code
  - implementation part that needs to be in ASM code, but
    is not MC version-specific
  - generally represented by classes with `ASM` suffix
- MC version-specific ASM code
  - implementation part that needs to be in ASM code and
    is MC version-specific
  - generally represented by classes with `ASM_X` suffix

Implementation was being refactored several times already due to
how MC internals sometimes drastically changes. Most notably:
- MC 1.13
  - many particle names were changed or merged two-into-one.
  - HUGE internal MC refactor happened there, basically changing
    how particles are represented in code
- Spigot 1.17
  - more parts of internals are remaining obfuscated in favor
    of using Mojang mappings
      - also moved all classes to other packages,
        this gives me nightmares
  - also funny `VIBRATION` particle being the most *abnormally spawned* particle
- MC 1.18
  - removed `LIGHT` particle in favor of `BLOCK_MARKER`
    - it literally existed for one version, lol
- MC 1.19
  - changed `VIBRATION` to be normally spawned particle, which also
    gives me nightmares about how to make it backward compatible

## Conventions in `core.asm` package
To organize such many classes, across several refactors I've formed my own
convention to some elements that I couldn't come up with better name
or structure.

Anything with `X` means MC version, for example `1_8`, `1_13` etc.

Those are:
- classes with `ASM` suffix
  - represents classes that does something with ASM
- classes with `ASM_X` suffix
  - represents classes that does something MC version-specific with ASM
  - some of them are also *skeletons* described below, which means they
    generate code for a class
- `vX` package (for example `v1_8`)
    - contains classes version-specific for MC `X` version
- `skeleton` packages
  - represents base class generation units
  - if it is in `vX` package
    - represents MC version-specific base class generation units
- variables with `local_` prefix
  - represents local variables of method body being written
    - they are used to avoid hard-coding indexes because JVM references
      local variables by indexes in local variable array for method code

## Package `core.asm`
The `core.asm` package basically consists of:
- `BaseASM` class
  - a base class of every other class that does *something* with ASM
- `ContextASM` class
  - class containing most useful objects during generation
  - selects classes for version-specific code
  - used to avoid passing 2-5 arguments to most classes
- `mapping` package
  - contains `ClassMapping` class
    - represents class and contains some useful data about it
    - heavily used in unit tests to mock MC internal classes
  - contains `SpigotClassRegistry`
    - contains all class mapping useful for class generation
    - heavily used in unit tests to mock MC internal classes
    - it is partially split by `1_7` and `1_17` suffixed mappings
      - this is how I handle Spigot mapping changes in `1_17` version
        and will handle in such way if future changes similarly happens
      - this is literally the best possible handling I came with, the previous 
        one was absolutely horrible
- `skeleton` package
  - contains `ClassSkeletonASM` class
    - a base class of every other class with suffix `ASM` that is
      responsible for generating bytecode of class without this suffix
  - contains `ClassSkeleton` class
    - an enum representing API classes with some useful data
    - used as arguments to `ASM_X` suffixed classes because some code can be shared
      between them
- `particle` package described below
- `particle.type` package described below
- `packet` package described below

### Package `core.asm.particle`
The `core.asm.particle` package consists of:
- `ParticleListProvider` class
  - responsible for selecting proper `ASM_X` providers of class bytecode
    based on current server version
  - creates `ParticleList_X` implementations using `ParticeListASM`
- `ParticleListASM` class
  - contains version-specific particle list code
  - uses `ParticleTypesProvider` to populate abstract method body
    with proper particle types

### Package `core.asm.particle.type`
The `core.asm.particle.type` package consists of:
- `particle.type` package
  - has `ParticleTypesProvider` abstract class and several classes
    with `_X` suffix that extends it
    - they are responsible for providing version-specific code
      of particle types
- `particle.type.skeleton` package
  - contains base classes for particle types
  - contains code non-version-specific
- `particle.type.vX` package for every `ParticleTypesProvider_X` class
  - contains version-specific particle types code for `X` version
- `particle.type.vX.skeleton`
  - contains version-specific particle types base code for `X` version

### Package `core.asm.packet`
The `core.asm.packet` package consists of:
- `packet` package
    - has `ParticlePacketProvider` abstract class and several classes
      with `_X` suffix that extends it
        - they are responsible for providing version-specific code
          of particle packet wrappers
- `packet.vX` package for every `ParticlePacketProvider_X` class
  - contains version-specific particle packet wrapper code for `X` version

### Package `core.asm.utils`
The `core.asm.utils` package consists of:
- `InternalResolver` class
  - mostly contains methods related to MC internal classes
- `SpigotVersion` class
  - an enum representing MC versions at which API implementation changes
- `SpigotParticleVersion` class
  - an enum representing MC versions at which particles internally
    significantly changes
- `ParticleRegistry` and `ParticleNode`
  - registry contains all MC particle presence changes between versions
  - it consists of lists of maps representing particle presence
    across MC versions
    - index of list is position in `SpigotParticleVersion`
    - key for map is name of particle in MC version from index above
    - value from map is object with data about particle presence
      and some useful methods to traverse name changes between MC versions
  
## Unit tests
Making unit tests was quite a bit challenging.
Not only I had to make tests for standard functionality, but also
if everything properly generated.

This was quite hard, because MC internals not only changes
significantly several times (so I can't reference internal classes in tests),
but also most important classes are dynamically created (so those I also can't
directly reference).

My general approach breaks few conventions that good unit tests
should meet, however, it allows to thoughtfully test API on all
significant MC versions:
- without actually directly referencing internal classes
- without running server to check if everything is fine
  - this allows to check with 95% certainty that everything with API
    is fine on **ALL versions in less than few seconds**

There are few good but also wrong things in unit tests:
- all API implementation on certain MC versions are instantiated once for
  all unit tests run
- internal MC classes are being mocked by custom classes that "mimics"
  real MC classes to small degree
- most unit test method names are too simple
  - some of them are literally named like `test_ParticleType`
    and they check several cases at once instead of one
  - if I have time, I split them and name them properly, however doing so
    with >200 unit tests will take some time.

Class `ParticeNativeCoreTest` contains API implementations initializations
for all MC versions.

This is done using `SpigotClassRegistryProvider` that should return
such mappings that represents state of classes in certain MC version.

I've structured implementation in such way, that everything MC internal
related is done using `SpigotClassRegistry` so we can *intercept* actual
class references in dynamically generated classes.

All implementations are loosely initialized, so if something is done wrong
in implementation, some (or most) unit tests will fail, but
you will almost certainly get the same error that you would
get when testing API on real server.

The drawback is that, whenever something incompatible happens
in next MC versions, you have to prepare new *state* of `SpigotParticeRegistry`
and add new unit tests in other classes checking their functionality.

## Manual tests
Manual tests take quite a bit of time.
Usually, to check if everything is fine, it takes around 2 hours to
- install some custom plugin with everything possible to use in API
- run server at `X` version
- join it with MC client
- check if every particle spawn properly according to radius, predicate etc
- check if every particle that should be present is spawned

... and do it several times for each significant version.

Unit tests covers 95% of possible errors that would otherwise be visible
only after actually running above manual tests.

The other 5% are wrong assumptions about MC internals that weren't properly
covered in unit tests or just some parts of start are hard to intercept.
