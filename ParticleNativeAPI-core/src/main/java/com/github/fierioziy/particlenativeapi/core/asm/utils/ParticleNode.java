package com.github.fierioziy.particlenativeapi.core.asm.utils;

/**
 * <p>A node used by <code>ParticleRegistry</code> to represent current
 * particle name in certain Spigot version using <code>ParticleVersion</code> enum.</p>
 *
 * <p>A <code>ParticleNode</code> has a structure similar to node in doubly linked list
 * that consist of reference to previous node and next node.</p>
 *
 * <p>It is used to find particle name in target Spigot version using particle name
 * in current Spigot version.</p>
 */
public class ParticleNode {

    private ParticleNode prev = null;
    private ParticleNode next = null;

    private ParticleVersion version;
    private String name;
    private boolean removed;

    /**
     * <p>Construct node representing certain particle based
     * on its name in provided Spigot version.</p>
     *
     * @param version a <code>ParticleVersion</code> to which particle name belongs.
     * @param name a name of this particle in provided Spigot version.
     */
    ParticleNode(ParticleVersion version, String name) {
        this(version, name, false);
    }

    ParticleNode(ParticleVersion version, String name, boolean removed) {
        this.version = version;
        this.name = name;
        this.removed = removed;
    }

    /**
     * <p>Creates new node that represents same particle name in next Spigot version.</p>
     *
     * @return a new node representing same particle name in next Spigot version
     * that is bound to this node.
     */
    ParticleNode follow() {
        ParticleNode node = new ParticleNode(getNextVersion(), name);
        this.next = node;
        node.prev = this;

        return node;
    }

    /**
     * <p>Creates new node that represents changed particle name in next Spigot version
     * in a full compatible manner.</p>
     *
     * @return a new node representing new particle name in next Spigot version
     * that is bound to this node in full compatible manner.
     */
    ParticleNode follow(String changedName) {
        ParticleNode node = new ParticleNode(getNextVersion(), changedName);
        this.next = node;
        node.prev = this;

        return node;
    }

    /**
     * <p>Creates new node that represents changed particle name in next Spigot version
     * in a forward compatible manner.</p>
     *
     * <p>It means, that future node can be accessed from this node, but current
     * node can't be accessed by future node.</p>
     *
     * @return a new node representing new particle name in next Spigot version
     * that is bound to this node in forward compatible manner.
     */
    ParticleNode followForward(String changedName) {
        ParticleNode node = new ParticleNode(getNextVersion(), changedName);
        this.next = node;
        node.prev = null;

        return node;
    }

    /**
     * <p>Creates new node that represents changed particle name in next Spigot version
     * in a backward compatible manner.</p>
     *
     * <p>It means, that future node can't be accessed from this node, but current
     * node can be accessed by future node.</p>
     *
     * @return a new node representing new particle name in next Spigot version
     * that is bound to this node in backward compatible manner.
     */
    ParticleNode followBackward(String changedName) {
        ParticleNode node = new ParticleNode(getNextVersion(), changedName);
        this.next = null;
        node.prev = this;

        return node;
    }

    /**
     * <p>Creates new node that indicates, that this particle is removed in next Spigot version.</p>
     *
     * @return a new node representing removed particle in next Spigot version
     * that is bound to this node.
     */
    ParticleNode followRemoved() {
        ParticleNode node = new ParticleNode(getNextVersion(), name, true);
        this.next = node;
        node.prev = this;

        return node;
    }

    /**
     * <p>Creates new node that indicates, that this particle is restored in next Spigot version.</p>
     *
     * @return a new node representing restored particle in next Spigot version
     * that is bound to this node.
     */
    ParticleNode followRestored() {
        ParticleNode node = new ParticleNode(getNextVersion(), name);
        this.next = node;
        node.prev = this;

        return node;
    }

    /**
     * <p>Binds this node to the parameter <code>ParticleNode</code> node.</p>
     *
     * <p>Roughly speaking, this method indicates, that this particle has been merged
     * with another particle in next Spigot version.</p>
     *
     * @return a parameter <code>ParticleNode</code> node.
     */
    ParticleNode follow(ParticleNode node) {
        this.next = node;
        return node;
    }

    /**
     * <p>Recursively attempts to find node of parameter Spigot version.</p>
     *
     * @param target an enum representing target Spigot version.
     * @return a found node with existing name in target Spigot version
     * or null, if node couldn't be found.
     */
    ParticleNode find(ParticleVersion target) {
        if (target.ordinal() < version.ordinal()) {
            return prev != null ? prev.find(target) : null;
        }
        else if (target.ordinal() > version.ordinal()) {
            return next != null ? next.find(target) : null;
        }
        return this;
    }

    /**
     * <p>Gets next spigot version based on this node.</p>
     *
     * @return a <code>ParticleVersion</code> enum representing
     * next Spigot version.
     */
    private ParticleVersion getNextVersion() {
        ParticleVersion[] arr = ParticleVersion.values();
        int next = version.ordinal() + 1;

        if (next >= arr.length) {
            throw new IndexOutOfBoundsException(
                    "ParticleVersion ordinal (name: " + name + " ) exceed bounds ("
                            + next + " >= " + arr.length + "."
            );
        }
        return arr[next];
    }

    /**
     * <p>Gets Spigot version from this node.</p>
     *
     * @return a <code>ParticleVersion</code> enum representing
     * Spigot version of this node.
     */
    public ParticleVersion getVersion() {
        return version;
    }

    /**
     * <p>Gets particle name in this Spigot version.</p>
     *
     * @return a particle name.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Checks if particle does not exists in this Spigot version.</p>
     *
     * @return true if particle does not exists in this Spigot
     * version, false otherwise.
     */
    public boolean isRemoved() {
        return removed;
    }

}