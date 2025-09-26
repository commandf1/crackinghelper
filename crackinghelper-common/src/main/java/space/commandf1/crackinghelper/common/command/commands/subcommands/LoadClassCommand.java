package space.commandf1.crackinghelper.common.command.commands.subcommands;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.common.command.SubCommand;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;
import space.commandf1.crackinghelper.common.util.ClassUtil;
import space.commandf1.crackinghelper.common.util.ListUtil;
import space.commandf1.crackinghelper.common.util.RuntimeUtil;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;

/**
 * @author commandf1
 */
public class LoadClassCommand extends SubCommand {
    public LoadClassCommand() {
        super("loadclass", null, "Load a class via a java code", "crackinghelper.command.main.loadclass", false);
    }

    @Override
    public List<String> onTabComplete(CommonCommandSender<?> sender, String[] args) {
        if (args.length == 1) {
            return ClassUtil.getLoadedClassloaders(IPluginController.getController().getInstrumentation()).stream().map(classLoader -> Integer.toHexString(classLoader.hashCode())).toList();
        }

        if (args.length == 3) {
            val files = IPluginController.getController().getJavaCodesFolder().listFiles();
            if (files == null) {
                return List.of();
            }
            return Arrays.stream(files).map(File::getName).toList();
        }

        return List.of();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args) {
        if (args.length < 3) {
            sender.sendMessage("/crackinghelper loadclass <parent classloader hashcode> <name> <file name> [args...]");
            return;
        }

        val classLoaderHashcode = args[0];
        val className = args[1];
        val fileName = args[2];

        val classLoader = ClassUtil.getClassLoaderByHashCode(classLoaderHashcode,
                IPluginController.getController().getInstrumentation()
        );

        if (classLoader.isEmpty()) {
            sender.sendMessage("ClassLoader with hashcode " + classLoaderHashcode + " not found!");
            return;
        }

        val classFile = new File(IPluginController.getController().getJavaCodesFolder(), fileName);
        if (!classFile.exists()) {
            sender.sendMessage("File " + classFile.getAbsolutePath() + " not found!");
            return;
        }

        try {
            val clazz = RuntimeUtil.loadClassWithSourceCode(
                    ListUtil.linesToString(Files.readAllLines(classFile.toPath())),
                    classLoader.get(),
                    className
            );

            sender.sendMessage("Loaded class " + clazz.getName() + " into classloader " + classLoader.get());
            sender.sendMessage("Class source: " + classFile.getAbsolutePath());
            sender.sendMessage("Class information: \n" + ClassUtil.getClassInfo(clazz));

            try {
                val mainMethod = clazz.getDeclaredMethod("main", String[].class);
                sender.sendMessage("main method detected!");
                if ((mainMethod.getModifiers() & STATIC) == 0) {
                    sender.sendMessage("main method is not static!");
                    return;
                }

                if ((mainMethod.getModifiers() & PUBLIC) == 0) {
                    sender.sendMessage("main method is not public!");
                    return;
                }

                val lookup = MethodHandles.lookup();
                sender.sendMessage("start executing...");
                sender.sendMessage("==============================");
                String[] arguments = args.length > 3 ? Arrays.copyOfRange(args, 3, args.length) : new String[0];
                lookup.findStatic(clazz, "main", MethodType.methodType(void.class, String[].class))
                        .asFixedArity()
                        .invoke((Object) arguments);
            } catch (NoSuchMethodException ignored) {
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            sender.sendMessage("Failed to load class " + className + " into " + classLoader.get() + "!");
            sender.sendMessage("Exception: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
