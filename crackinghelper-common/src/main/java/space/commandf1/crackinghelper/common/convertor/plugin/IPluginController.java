package space.commandf1.crackinghelper.common.convertor.plugin;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author commandf1
 */
public interface IPluginController {
    List<? extends CommonPlugin<?>> getPlugins();

    Optional<? extends CommonPlugin<?>> getPluginByName(String name);

    Logger getLogger();
    
    Instrumentation getInstrumentation();

    CommonPlugin<?> currentPlugin();

    File getJavaCodesFolder();

    File currentDataFolder();

    default void runTaskAsynchronously(Runnable runnable) {
        new Thread(runnable).start();
    }

    void runTaskSynchronously(Runnable runnable);

    static IPluginController getController() {
        return CommonPlugin.controller;
    }
}
