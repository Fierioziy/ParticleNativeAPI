package com.github.fierioziy.particlenativeapi.api.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class FakePacket {

    private boolean far;
    private double x, y, z;
    private double offsetX, offsetY, offsetZ;
    private double speed;
    private int count;

    public FakePacket(boolean far, double x, double y, double z,
                      double offsetX, double offsetY, double offsetZ,
                      double speed, int count) {
        this.far = far;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.count = count;
    }

    public Location getLocation() {
        return new Location(null, x, y, z);
    }

    public Vector getVector() {
        return new Vector(x, y, z);
    }

    public boolean isFar() {
        return far;
    }

    public void setFar(boolean far) {
        this.far = far;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetZ(double offsetZ) {
        this.offsetZ = offsetZ;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
