package com.github.fierioziy.particlenativeapi.core.mocks;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import static org.mockito.Mockito.mock;

public abstract class StaticMock {

    private static final Field bukkitServerField;

    static {
        try {
            Field serverField = Bukkit.class.getDeclaredField("server");
            serverField.setAccessible(true);
            bukkitServerField = serverField;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private StaticMock() {}

    public static void ofServer(Consumer<Server> consumer) {
        try {
            Server mockServer = mock(Server.class);

            bukkitServerField.set(null, mockServer);
            consumer.accept(mockServer);
            bukkitServerField.set(null, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
