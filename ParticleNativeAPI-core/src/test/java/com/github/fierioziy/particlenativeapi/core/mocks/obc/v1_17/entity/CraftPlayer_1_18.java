package com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_17.entity;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.EntityPlayer_1_18;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/*
It has to be abstract, we don't want to implement tons of Player's methods.
 */
public abstract class CraftPlayer_1_18 implements Player {

    public String name;
    public EntityPlayer_1_18 ep;

    public CraftPlayer_1_18() {}

    // required
    public EntityPlayer_1_18 getHandle() {
        return ep;
    }

    // required
    @Override
    public Location getLocation() {
        return new Location(null, ep.x, ep.y, ep.z);
    }

    @Override
    public String getName() {
        return name;
    }

}
