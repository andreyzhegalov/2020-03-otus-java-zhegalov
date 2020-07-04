package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.config.components.EquationPrepareConfig;
import ru.otus.config.components.GameProcessorConfig;
import ru.otus.config.components.IOServiceConfig;
import ru.otus.config.components.PlayerServiveConfig;
import ru.otus.services.GameProcessor;


public class AppWithSeparateConfig {

    public static void main(String[] args) throws Exception {
        final var container = new AppComponentsContainerImpl(PlayerServiveConfig.class, IOServiceConfig.class,
                EquationPrepareConfig.class, GameProcessorConfig.class);
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        gameProcessor.startGame();
    }
}
