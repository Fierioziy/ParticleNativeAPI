package com.github.fierioziy.particlenativeapi.core.mocks;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;

public class StaticMockServerExtension
        implements BeforeTestExecutionCallback, AfterTestExecutionCallback, ParameterResolver {

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

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        Server mockServer = mock(Server.class);
        bukkitServerField.set(null, mockServer);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        bukkitServerField.set(null, null);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Server.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        try {
            return bukkitServerField.get(null);
        } catch (IllegalAccessException e) {
            throw new ParameterResolutionException("Could not inject bukkit server mock", e);
        }
    }

}
