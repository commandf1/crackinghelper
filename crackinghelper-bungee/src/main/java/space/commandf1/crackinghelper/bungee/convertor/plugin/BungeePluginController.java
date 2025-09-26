package space.commandf1.crackinghelper.bungee.convertor.plugin;

import lombok.val;
import net.md_5.bungee.api.ProxyServer;
import space.commandf1.crackinghelper.bungee.BungeeCrackingHelperPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.CommonPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author commandf1
 */
public class BungeePluginController implements IPluginController {
    @Override
    public void runTaskAsynchronously(Runnable runnable) {
        ProxyServer.getInstance().getScheduler().runAsync(BungeeCrackingHelperPlugin.getInstance(), runnable);
    }

    @Override
    public void runTaskSynchronously(Runnable runnable) {
        this.runTaskAsynchronously(runnable);
    }

    @Override
    public List<? extends CommonPlugin<?>> getPlugins() {
        return ProxyServer.getInstance().getPluginManager().getPlugins()
                .stream()
                .map(BungeePlugin::new)
                .toList();
    }

    @Override
    public Optional<? extends CommonPlugin<?>> getPluginByName(String name) {
        val plugin = ProxyServer.getInstance().getPluginManager().getPlugin(name);
        if (plugin == null) {
            return Optional.empty();
        } else {
            return Optional.of(new BungeePlugin(plugin));
        }
    }

    @Override
    public Logger getLogger() {
        return BungeeCrackingHelperPlugin.getInstance().getLogger();
    }

    @Override
    public Instrumentation getInstrumentation() {
        return BungeeCrackingHelperPlugin.getInstrumentation();
    }

    @Override
    public CommonPlugin<?> currentPlugin() {
        return new BungeePlugin(BungeeCrackingHelperPlugin.getInstance());
    }

    @Override
    public File getJavaCodesFolder() {
        return BungeeCrackingHelperPlugin.getInstance().getJavaCodesFolder();
    }

    @Override
    public File currentDataFolder() {
        return BungeeCrackingHelperPlugin.getInstance().getDataFolder();
    }
}
