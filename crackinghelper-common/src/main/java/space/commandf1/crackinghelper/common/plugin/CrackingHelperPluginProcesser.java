package space.commandf1.crackinghelper.common.plugin;

import lombok.val;
import net.bytebuddy.agent.ByteBuddyAgent;
import space.commandf1.crackinghelper.common.convertor.plugin.CommonPlugin;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.common.util.ListUtil;
import space.commandf1.crackinghelper.common.util.RuntimeUtil;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.file.Files;
import java.util.Arrays;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;

/**
 * @author commandf1
 */
public class CrackingHelperPluginProcesser {
    public CrackingHelperPluginProcesser(IPluginController controller) {
        CommonPlugin.registerController(controller);
    }

    public Instrumentation initForAgent() {
        try {
            return ByteBuddyAgent.install();
        } catch (Exception e) {
            IPluginController.getController()
                    .getLogger()
                    .severe("Failed to install ByteBuddy agent: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void unloadForAgent() {
        val instrumentation = IPluginController.getController().getInstrumentation();
        val logger = IPluginController.getController().getLogger();

        try {
            instrumentation.retransformClasses(instrumentation.getAllLoadedClasses());
        } catch (Exception e) {
            logger.warning("Failed to reset transformed classes: " + e.getMessage());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public File initForJavaCodes() {
        val toReturn = new File(IPluginController.getController().currentDataFolder(), "java-codes");
        if (!toReturn.exists()) {
            toReturn.mkdirs();
        }

        val javaCodes = toReturn.listFiles();
        if (javaCodes != null) {
            Arrays.stream(javaCodes).filter(file -> file.getName().toLowerCase().endsWith(".autorun")).forEach(file -> {
                Class<?> clazz;
                try {
                    clazz = RuntimeUtil.loadClassWithSourceCode(
                            ListUtil.linesToString(Files.readAllLines(file.toPath())),
                            IPluginController.getController().currentPlugin().getPluginClassLoader(),
                            "AutoRun"
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    val mainMethod = clazz.getDeclaredMethod("main", String[].class);
                    if ((mainMethod.getModifiers() & STATIC) == 0) {
                        return;
                    }

                    if ((mainMethod.getModifiers() & PUBLIC) == 0) {
                        return;
                    }

                    val lookup = MethodHandles.lookup();
                    String[] arguments = new String[0];
                    lookup.findStatic(clazz, "main", MethodType.methodType(void.class, String[].class))
                            .asFixedArity()
                            .invoke((Object) arguments);
                } catch (NoSuchMethodException ignored) {
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return toReturn;
    }
}
