package me.fierioziy.asm.utils;

import me.fierioziy.utils.TempClassLoader;
import org.objectweb.asm.MethodVisitor;

/**
 * <p>An interface used by classes generating
 * implementation bytecode of particle types.</p>
 *
 * <p>A <code>ParticlesASM</code> class uses it
 * to handle cross-version particle type implementation.</p>
 */
public interface ClassImplProvider {

    /**
     * <p>Defines all implementations of certain particle
     * types version on parameter class loader.</p>
     *
     * @param cl a class loader on which implementation
     *           should be defined.
     */
    void defineImplementation(TempClassLoader cl);

    /**
     * <p>Visits all particle types implementation
     * on parameter <code>MethodVisitor</code> using
     * parameter interface version.</p>
     *
     * <p>Technically speaking, it generates bytecode for
     * instantiating all particle types implementations
     * and storing them in instance fields.</p>
     *
     * @param mv a <code>MethodVisitor</code> on which
     *           instructions visiting occurs.
     * @param interfaceVersion a <code>ParticleVersion</code> enum
     *                         providing informations about processed
     *                         interface version.
     */
    void visitParticleTypes(MethodVisitor mv, ParticleVersion interfaceVersion);
}
