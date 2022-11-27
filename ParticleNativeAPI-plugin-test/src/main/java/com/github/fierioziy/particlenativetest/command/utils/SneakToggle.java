package com.github.fierioziy.particlenativetest.command.utils;

public class SneakToggle {

    private static final int SNEAK_WAIT_TICKS = 10;
    private static final int SNEAK_FINISH_COUNT = 3;

    private int sneakTicks = 0;
    private int sneakCount = 0;
    private boolean sneakLock = false;

    private boolean finished = false;

    public void tick(boolean isSneaking) {
        if (isSneaking && !sneakLock) {
            ++sneakCount;
            sneakTicks = SNEAK_WAIT_TICKS;
            sneakLock = true;
            if (sneakCount >= SNEAK_FINISH_COUNT) {
                finished = true;
            }
        }

        if (!isSneaking) {
            sneakLock = false;
            sneakTicks = sneakTicks > 0 ? --sneakTicks : 0;
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
