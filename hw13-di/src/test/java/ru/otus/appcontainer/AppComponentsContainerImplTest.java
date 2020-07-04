package ru.otus.appcontainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.otus.services.GameProcessor;
import ru.otus.services.GameProcessorImpl;
import ru.otus.services.IOService;
import ru.otus.services.IOServiceConsole;
import ru.otus.testconfig.AppConfig;
import ru.otus.testconfig.components.EquationPrepareConfig;
import ru.otus.testconfig.components.GameProcessorConfig;
import ru.otus.testconfig.components.IOServiceConfig;
import ru.otus.testconfig.components.PlayerServiveConfig;

public class AppComponentsContainerImplTest {

    @Test
    public void ctrTestWithNotValidClassConfig() {
        assertThrows(IllegalArgumentException.class, () -> new AppComponentsContainerImpl(Object.class));
    }

    @Test
    public void ctrTestWithValidClassConfig() {
        assertDoesNotThrow(() -> new AppComponentsContainerImpl(AppConfig.class));
    }

    @Test
    public void getComponentWithOutArgsByImplementationTest() {
        final IOServiceConsole ioServiceConsole = new AppComponentsContainerImpl(AppConfig.class)
                .getAppComponent(IOServiceConsole.class);
        assertNotNull(ioServiceConsole);
        assertThat(ioServiceConsole).isInstanceOf(IOServiceConsole.class);
    }

    @Test
    public void getComponentWithOutArgsByInterfaceTest() {
        final IOServiceConsole ioServiceConsole = (IOServiceConsole) new AppComponentsContainerImpl(AppConfig.class)
                .getAppComponent(IOService.class);
        assertNotNull(ioServiceConsole);
        assertThat(ioServiceConsole).isInstanceOf(IOServiceConsole.class);
    }

    @Test
    public void getComponentWithArgsTest() {
        final GameProcessorImpl gameProcessor = (GameProcessorImpl) new AppComponentsContainerImpl(AppConfig.class)
                .getAppComponent(GameProcessor.class);
        assertNotNull(gameProcessor);
        assertThat(gameProcessor).isInstanceOf(GameProcessorImpl.class);
    }

    @Test
    public void getComponentNotFromConfig() {
        final String someObject = (String) new AppComponentsContainerImpl(AppConfig.class)
                .getAppComponent(String.class);
        assertThat(someObject).isNull();
    }

    @Test
    public void getComponentByNameWithArgs() {
        final GameProcessorImpl gameProcessor = (GameProcessorImpl) new AppComponentsContainerImpl(AppConfig.class)
                .getAppComponent("gameProcessor");
        assertNotNull(gameProcessor);
        assertThat(gameProcessor).isInstanceOf(GameProcessorImpl.class);
    }

    @Test
    public void getComponentByNameNotFromConfig() {
        final String someObject = (String) new AppComponentsContainerImpl(AppConfig.class).getAppComponent("String");
        assertThat(someObject).isNull();
    }

    @Test
    public void loadConfigFromManyFiles() {
        final var appComponentContainerImpl = new AppComponentsContainerImpl(PlayerServiveConfig.class,
                IOServiceConfig.class, EquationPrepareConfig.class, GameProcessorConfig.class);
        final var gameProcessor = appComponentContainerImpl.getAppComponent(GameProcessor.class);
        assertThat(gameProcessor).isNotNull();
        assertThat(gameProcessor).isInstanceOf(GameProcessorImpl.class);
    }
}
