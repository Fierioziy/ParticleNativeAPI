package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13;

public class ParticleParamBlock implements ParticleParam {

    public Particle particle;
    public IBlockData blockData;

    // required
    public ParticleParamBlock(Particle particle, IBlockData blockData) {
        this.particle = particle;
        this.blockData = blockData;
    }

    // simple reference compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleParamBlock)) {
            return false;
        }

        ParticleParamBlock param = (ParticleParamBlock) obj;
        return particle == param.particle && blockData == param.blockData;
    }

}
