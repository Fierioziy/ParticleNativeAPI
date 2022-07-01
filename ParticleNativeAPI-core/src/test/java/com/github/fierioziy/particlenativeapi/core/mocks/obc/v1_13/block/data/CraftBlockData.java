package com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.IBlockData;
import org.bukkit.block.data.BlockData;

public abstract class CraftBlockData implements BlockData {

    public IBlockData iBlockData;

    @Override
    public BlockData clone() {
        return forwardCloneSoMockitoWontComplain();
    }

    public abstract BlockData forwardCloneSoMockitoWontComplain();

    // required
    public IBlockData getState() {
        return iBlockData;
    }

}
