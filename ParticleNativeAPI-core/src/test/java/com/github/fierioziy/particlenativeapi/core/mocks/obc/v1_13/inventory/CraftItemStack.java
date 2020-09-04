package com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;

public class CraftItemStack {

    public static ItemStack nmsItemStack = null;

    // required
    public static ItemStack asNMSCopy(org.bukkit.inventory.ItemStack bukkitStack) {
        return nmsItemStack;
    }

}
