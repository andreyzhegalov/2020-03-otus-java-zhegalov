package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.services.GameProcessor;

public class AppWithConfigFromPackage {

    public static void main(String[] args) throws Exception {
        final var container = new AppComponentsContainerImpl("ru.otus.config.components");
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        gameProcessor.startGame();
    }
}
