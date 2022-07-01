package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13;

import org.bukkit.Material;

public class IBlockDataMock implements IBlockData {

    // non-existent, used for equality checks
    public final Material material;

    public IBlockDataMock(Material material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IBlockDataMock)) return false;

        IBlockDataMock that = (IBlockDataMock) o;

        return material == that.material;
    }

}
