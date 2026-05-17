package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.module.ModuleFinder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

@Configuration
class ModuleConfig {

    public ModuleConfig() {
    }

    @Bean
    public Game game() {
        return new Game(
            gamePluginServices(),
            entityProcessingServiceList(),
            postEntityProcessingServices()
        );
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServiceList() {
        var services = new ArrayList<IEntityProcessingService>();
        // load from boot layer (Player, Asteroid etc)
        ServiceLoader.load(IEntityProcessingService.class)
            .stream()
            .map(ServiceLoader.Provider::get)
            .forEach(services::add);
        // load from plugin layer (Enemy)
        ServiceLoader.load(pluginLayer(), IEntityProcessingService.class)
            .stream()
            .map(ServiceLoader.Provider::get)
            .forEach(services::add);
        return services;
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        var services = new ArrayList<IGamePluginService>();
        ServiceLoader.load(IGamePluginService.class)
            .stream()
            .map(ServiceLoader.Provider::get)
            .forEach(services::add);
        ServiceLoader.load(pluginLayer(), IGamePluginService.class)
            .stream()
            .map(ServiceLoader.Provider::get)
            .forEach(services::add);
        return services;
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        var services = new ArrayList<IPostEntityProcessingService>();
        ServiceLoader.load(IPostEntityProcessingService.class)
            .stream()
            .map(ServiceLoader.Provider::get)
            .forEach(services::add);
        ServiceLoader.load(pluginLayer(), IPostEntityProcessingService.class)
            .stream()
            .map(ServiceLoader.Provider::get)
            .forEach(services::add);
        return services;
    }

    private ModuleLayer pluginLayer() {
        var pluginsPath = Path.of("plugins");
        var finder = ModuleFinder.of(pluginsPath);

        var moduleNames = finder.findAll()
            .stream()
            .map(m -> m.descriptor().name())
            .collect(toList());

        // create a child layer on top of the boot layer
        var bootLayer = ModuleLayer.boot();
        var config = bootLayer.configuration()
            .resolve(finder, ModuleFinder.of(), moduleNames);

        var classLoader = ClassLoader.getSystemClassLoader();
        return ModuleLayer
            .defineModulesWithOneLoader(config, List.of(bootLayer), classLoader)
            .layer();
    }
}
