package space.commandf1.crackinghelper.bukkit;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import space.commandf1.crackinghelper.bukkit.command.BukkitCrackingHelperCommand;
import space.commandf1.crackinghelper.bukkit.convertor.plugin.BukkitPluginController;
import space.commandf1.crackinghelper.common.command.CommandManager;
import space.commandf1.crackinghelper.common.plugin.CrackingHelperPluginProcesser;
import space.commandf1.crackinghelper.common.tracker.TrackerManager;
import space.commandf1.crackinghelper.common.tracker.trackers.NetworkTracker;

import java.io.File;
import java.lang.instrument.Instrumentation;

/**
 * @author commandf1
 */
public class BukkitCrackingHelperPlugin extends JavaPlugin {
    @Getter
    private static BukkitCrackingHelperPlugin instance;

    @Getter
    private static Instrumentation instrumentation;

    @Getter
    private File javaCodesFolder;

    @Getter
    private CrackingHelperPluginProcesser processer;

    @Override
    public void onEnable() {
        CommandManager.getManager().registerCommands(new BukkitCrackingHelperCommand());
    }

    @Override
    public void onDisable() {
        if (instrumentation != null) {
            processer.unloadForAgent();
        }

        instance = null;
        instrumentation = null;
    }

    @Override
    public void onLoad() {
        instance = this;

        this.saveDefaultConfig();

        this.processer = new CrackingHelperPluginProcesser(new BukkitPluginController());
        instrumentation = processer.initForAgent();
        this.javaCodesFolder = processer.initForJavaCodes();

        if (this.getConfig().getBoolean("network-tracker.enabled")) {
            TrackerManager.getManager().register(new NetworkTracker(this.getConfig().getBoolean("network-tracker.detect-response")), this.getLogger());
        }
    }
}