package space.commandf1.crackinghelper.velocity;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import lombok.val;
import space.commandf1.crackinghelper.common.command.CommandManager;
import space.commandf1.crackinghelper.common.plugin.CrackingHelperPluginProcesser;
import space.commandf1.crackinghelper.common.tracker.TrackerManager;
import space.commandf1.crackinghelper.common.tracker.trackers.NetworkTracker;
import space.commandf1.crackinghelper.velocity.command.VelocityCrackingHelperCommand;
import space.commandf1.crackinghelper.velocity.convertor.plugin.VelocityPluginController;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * @author commandf1
 */
@Plugin(id = "crackinghelper", authors = "commandf1")
@Getter
public class VelocityCrackingHelperPlugin {
    @Getter
    private static VelocityCrackingHelperPlugin instance;

    @Getter
    private static Instrumentation instrumentation;

    private final ProxyServer server;
    private final Logger logger;

    private final CrackingHelperPluginProcesser processer;

    private final File dataFolder;

    @Getter
    private final File javaCodesFolder;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private Toml loadConfig(Path path) {
        File folder = path.toFile();


        File file = new File(folder, "config.toml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try (val input = this.getClass().getResourceAsStream("/" + file.getName())) {
                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new Toml().read(file);
    }

    @Getter
    private final Toml config;


    @Inject
    public VelocityCrackingHelperPlugin(ProxyServer server,
                                        Logger logger,
                                        @DataDirectory final Path folder) {
        instance = this;
        this.server = server;
        this.logger = logger;
        this.dataFolder = folder.toFile();

        val toml = this.loadConfig(folder);
        if (toml == null) {
            throw new RuntimeException("Failed to load config.toml. Shutting down.");
        }

        this.config = toml;

        this.processer = new CrackingHelperPluginProcesser(new VelocityPluginController());
        instrumentation = this.processer.initForAgent();
        this.javaCodesFolder = this.processer.initForJavaCodes();

        if (toml.getTable("network-tracker").getBoolean("enabled")) {
            TrackerManager.getManager().register(new NetworkTracker(toml.getTable("network-tracker").getBoolean("detect-response")), this.getLogger());
        }
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        CommandManager.getManager().registerCommands(new VelocityCrackingHelperCommand());
    }

}
