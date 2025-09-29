package space.commandf1.crackinghelper.bungee;

import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import space.commandf1.crackinghelper.bungee.command.BungeeCrackingHelperCommand;
import space.commandf1.crackinghelper.bungee.convertor.plugin.BungeePluginController;
import space.commandf1.crackinghelper.common.command.CommandManager;
import space.commandf1.crackinghelper.common.plugin.CrackingHelperPluginProcesser;
import space.commandf1.crackinghelper.common.tracker.TrackerManager;
import space.commandf1.crackinghelper.common.tracker.trackers.NetworkTracker;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;

/**
 * @author commandf1
 */
public class BungeeCrackingHelperPlugin extends Plugin {
    @Getter
    private static BungeeCrackingHelperPlugin instance;

    @Getter
    private static Instrumentation instrumentation;

    @Getter
    private File javaCodesFolder;

    @Getter
    private CrackingHelperPluginProcesser processer;

    private Configuration configuration;

    @Override
    public void onEnable() {
        CommandManager.getManager().registerCommands(new BungeeCrackingHelperCommand());
    }

    @Override
    public void onLoad() {
        instance = this;

        this.saveDefaultConfig();

        this.processer = new CrackingHelperPluginProcesser(new BungeePluginController());
        instrumentation = processer.initForAgent();
        this.javaCodesFolder = processer.initForJavaCodes();

        if (this.getConfig().getBoolean("network-tracker.enabled")) {
            TrackerManager.getManager().register(new NetworkTracker(this.getConfig().getBoolean("network-tracker.detect-response")), this.getLogger());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    private void saveDefaultConfig() {
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            this.getDataFolder().mkdirs();
            Files.copy(this.getResourceAsStream("config.yml"),
                    configFile.toPath());
        }

        this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    }

    public Configuration getConfig() {
        return this.configuration;
    }

    @Override
    public void onDisable() {
        if (instrumentation != null) {
            this.processer.unloadForAgent();
        }

        instance = null;
        instrumentation = null;
    }
}
