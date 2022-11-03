package com.github.fierioziy.particlenativeapi.api.utils;

public class ParticleException extends RuntimeException {

    public ParticleException() {
        super();
    }

    public ParticleException(String message) {
        super(message);
    }

    public ParticleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParticleException(Throwable cause) {
        super(cause);
    }

}
