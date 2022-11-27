package com.github.fierioziy.particlenativebenchmark;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import com.github.fierioziy.particlenativebenchmark.command.CommandPNAB;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticleNativeBenchmarkPlugin extends JavaPlugin {

    private boolean isValid = false;

    private ParticleNativeAPI particleApi;

    @Override
    public void onLoad() {
        particleApi = null;
        isValid = false;

        try {
            particleApi = ParticleNativeCore.loadAPI(this);
            isValid = true;
        }
        catch (ParticleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        this.getCommand("pnab").setExecutor(new CommandPNAB(this));
        this.setEnabled(isValid);
    }

    @Override
    public void onDisable() {
        particleApi = null;
        isValid = false;
    }

    public ParticleNativeAPI getAPI() {
        if (particleApi == null) {
            throw new IllegalStateException("Error occurred while getting particle library.");
        }

        return particleApi;
    }

}
