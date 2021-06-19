package com.github.fierioziy.particlenativeapi.core.mocks.obc.common.entity;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/*
It has to be abstract, we don't want to implement tons of Player's methods.
 */
public abstract class CraftPlayer implements Player {

    public String name;
    public EntityPlayer ep;

    public CraftPlayer() {}

    // required
    public EntityPlayer getHandle() {
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
