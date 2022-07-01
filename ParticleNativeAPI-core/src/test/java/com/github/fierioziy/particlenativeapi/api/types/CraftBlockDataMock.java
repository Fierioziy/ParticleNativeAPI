package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.IBlockDataMock;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import org.bukkit.Material;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class CraftBlockDataMock {

    private CraftBlockDataMock() {}

    public static CraftBlockData of(Material material) {
        CraftBlockData craftBlockDataMock = mock(CraftBlockData.class);
        craftBlockDataMock.iBlockData = new IBlockDataMock(material);
        when(craftBlockDataMock.getState()).thenCallRealMethod();

        return craftBlockDataMock;
    }

}
