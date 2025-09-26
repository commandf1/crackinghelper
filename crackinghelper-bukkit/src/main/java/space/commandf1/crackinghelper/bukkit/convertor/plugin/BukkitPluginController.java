package space.commandf1.crackinghelper.bukkit.convertor.plugin;

import lombok.val;
import org.bukkit.Bukkit;
import space.commandf1.crackinghelper.bukkit.BukkitCrackingHelperPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.CommonPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author commandf1
 */
public class BukkitPluginController implements IPluginController {
    @Override
    public void runTaskAsynchronously(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(BukkitCrackingHelperPlugin.getInstance(), runnable);
    }

    @Override
    public void runTaskSynchronously(Runnable runnable) {
        Bukkit.getScheduler().runTask(BukkitCrackingHelperPlugin.getInstance(), runnable);
    }

    @Override
    public List<? extends CommonPlugin<?>> getPlugins() {
        return Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .map(plugin -> (CommonPlugin<?>) new BukkitPlugin(plugin))
                .toList();
    }

    @Override
    public Optional<? extends CommonPlugin<?>> getPluginByName(String name) {
        val plugin = Bukkit.getPluginManager().getPlugin(name);
        if (plugin == null) {
            return Optional.empty();
        }

        return Optional.of(new BukkitPlugin(plugin));
    }

    @Override
    public Logger getLogger() {
        return BukkitCrackingHelperPlugin.getInstance().getLogger();
    }

    @Override
    public Instrumentation getInstrumentation() {
        return BukkitCrackingHelperPlugin.getInstrumentation();
    }

    @Override
    public CommonPlugin<?> currentPlugin() {
        return new BukkitPlugin(BukkitCrackingHelperPlugin.getInstance());
    }

    @Override
    public File getJavaCodesFolder() {
        return BukkitCrackingHelperPlugin.getInstance().getJavaCodesFolder();
    }

    @Override
    public File currentDataFolder() {
        return BukkitCrackingHelperPlugin.getInstance().getDataFolder();
    }
}
