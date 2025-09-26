package space.commandf1.crackinghelper.velocity.convertor.plugin;

import lombok.val;
import space.commandf1.crackinghelper.common.convertor.plugin.CommonPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.velocity.VelocityCrackingHelperPlugin;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author commandf1
 */
public class VelocityPluginController implements IPluginController {

    @Override
    public void runTaskAsynchronously(Runnable runnable) {
        VelocityCrackingHelperPlugin.getInstance().getServer().getScheduler().buildTask(VelocityCrackingHelperPlugin.getInstance(), runnable);
    }

    @Override
    public void runTaskSynchronously(Runnable runnable) {
        this.runTaskAsynchronously(runnable);
    }

    @Override
    public List<? extends CommonPlugin<?>> getPlugins() {
        return VelocityCrackingHelperPlugin.getInstance()
                .getServer()
                .getPluginManager()
                .getPlugins()
                .stream()
                .map(plugin -> (CommonPlugin<?>) new VelocityPlugin(plugin))
                .toList();
    }

    @Override
    public Optional<? extends CommonPlugin<?>> getPluginByName(String name) {
        val plugin = VelocityCrackingHelperPlugin.getInstance()
                .getServer()
                .getPluginManager()
                .getPlugin(name);
        if (plugin.isPresent()) {
            return Optional.of(new VelocityPlugin(plugin.get()));
        }

        return Optional.empty();
    }

    @Override
    public Logger getLogger() {
        return VelocityCrackingHelperPlugin.getInstance().getLogger();
    }

    @Override
    public Instrumentation getInstrumentation() {
        return VelocityCrackingHelperPlugin.getInstrumentation();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public CommonPlugin<?> currentPlugin() {
        return new VelocityPlugin(VelocityCrackingHelperPlugin.getInstance()
                .getServer()
                .getPluginManager()
                .fromInstance(VelocityCrackingHelperPlugin.getInstance())
                .get()
        );
    }

    @Override
    public File getJavaCodesFolder() {
        return VelocityCrackingHelperPlugin.getInstance().getJavaCodesFolder();
    }

    @Override
    public File currentDataFolder() {
        return VelocityCrackingHelperPlugin.getInstance().getDataFolder();
    }
}
