package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;

public class ParticleParamItem implements ParticleParam {

    public Particle particle;
    public ItemStack itemStack;

    // required
    public ParticleParamItem(Particle particle, ItemStack itemStack) {
        this.particle = particle;
        this.itemStack = itemStack;
    }

    // simple reference and value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleParamItem)) {
            return false;
        }

        ParticleParamItem param = (ParticleParamItem) obj;
        return particle == param.particle
                && itemStack.bukkitStack.getType() == param.itemStack.bukkitStack.getType()
                && itemStack.bukkitStack.getAmount() == param.itemStack.bukkitStack.getAmount();
    }

}
