package ru.otus.appcontainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.otus.appcontainer.api.AppComponentsContainer;
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
        final AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
        final IOService ioServiceConsole = container.getAppComponent(IOServiceConsole.class);
        assertThat(ioServiceConsole).isNotNull();
        assertThat(ioServiceConsole).isInstanceOf(IOServiceConsole.class);
    }

    @Test
    public void getComponentWithOutArgsByInterfaceTest() {
        final AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
        final IOService ioServiceConsole = container.getAppComponent(IOService.class);
        assertThat(ioServiceConsole).isNotNull();
        assertThat(ioServiceConsole).isInstanceOf(IOServiceConsole.class);
    }

    @Test
    public void getComponentWithArgsTest() {
        final AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
        final GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        assertThat(gameProcessor).isNotNull();
        assertThat(gameProcessor).isInstanceOf(GameProcessorImpl.class);
    }

    @Test
    public void getComponentNotFromConfigTest() {
        final AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
        final String someObject = container.getAppComponent(String.class);
        assertThat(someObject).isNull();
    }

    @Test
    public void getComponentByNameWithArgsTest() {
        final AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
        final GameProcessor gameProcessor = container.getAppComponent("gameProcessor");
        assertThat(gameProcessor).isNotNull();
        assertThat(gameProcessor).isInstanceOf(GameProcessorImpl.class);
    }

    @Test
    public void getComponentByNameNotFromConfigTest() {
        final AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
        final String someObject = container.getAppComponent("String");
        assertThat(someObject).isNull();
    }

    @Test
    public void loadConfigFromManyFilesTest() {
        final AppComponentsContainer appComponentContainerImpl = new AppComponentsContainerImpl(
                PlayerServiveConfig.class, IOServiceConfig.class, EquationPrepareConfig.class,
                GameProcessorConfig.class);
        final GameProcessor gameProcessor = appComponentContainerImpl.getAppComponent(GameProcessor.class);
        assertThat(gameProcessor).isNotNull();
        assertThat(gameProcessor).isInstanceOf(GameProcessorImpl.class);
    }

    @Test
    public void loadConfigFromPackageNameTest() {
        final AppComponentsContainer appComponentContainerImpl = new AppComponentsContainerImpl(
                "ru.otus.testconfig.components");
        final GameProcessor gameProcessor = appComponentContainerImpl.getAppComponent(GameProcessor.class);
        assertThat(gameProcessor).isNotNull();
        assertThat(gameProcessor).isInstanceOf(GameProcessorImpl.class);
    }
}
