package space.commandf1.crackinghelper.common.command.commands.subcommands;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.common.command.SubCommand;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;
import space.commandf1.crackinghelper.common.util.ClassUtil;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

/**
 * @author commandf1
 */
public class DumpCommand extends SubCommand {
    public DumpCommand() {
        super("dump", null, "Dump classes loaded to class file", "crackinghelper.command.main.dump", false);
    }

    @Override
    public List<String> onTabComplete(CommonCommandSender<?> sender, String[] args) {
        if (args.length == 1) {
            return ClassUtil.getLoadedClassloaders(IPluginController.getController().getInstrumentation()).stream().map(classLoader -> Integer.toHexString(classLoader.hashCode())).toList();
        }

        if (args.length != 2) {
            return List.of();
        }

        return List.of("*");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void dumpClass(CommonCommandSender<?> sender, Class<?> clazz) {
        try {
            val classBytes = ClassUtil.getClassBytes(clazz);
            val targetFile = new File(new File(IPluginController.getController().currentDataFolder(), "dumps"), clazz.getName().replace(".", File.separator) + ".class");

            targetFile.delete();
            File tmp = targetFile.getParentFile();
            while (tmp != null && !tmp.exists()) {
                tmp.mkdirs();
            }
            val result = Files.createFile(targetFile.toPath());
            Files.write(result, classBytes);
            IPluginController.getController().runTaskSynchronously(() ->
                    sender.sendMessage("Dumped " + clazz.getName() + " to " + targetFile.getAbsolutePath())
            );
        } catch (Exception e) {
            IPluginController.getController().runTaskSynchronously(() -> {
                sender.sendMessage("Failed to dump " + clazz.getName());
                sender.sendMessage("Exception: " + e.getLocalizedMessage());
            });
        }
    }

    @Override
    public void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage("/crackinghelper dump <classloader hashcode> <package name>");
            return;
        }

        val classLoaderHashcode = args[0];
        val packageName = args[1];

        val instrumentation = IPluginController.getController().getInstrumentation();
        val classLoader = ClassUtil.getClassLoaderByHashCode(classLoaderHashcode, instrumentation);
        classLoader.ifPresentOrElse(loader -> IPluginController.getController().runTaskAsynchronously(() -> {
            ClassUtil.getAllClassesByClassLoader(loader, instrumentation)
                    .values()
                    .stream()
                    .filter(clazz -> "*".equals(packageName) || clazz.getName().startsWith(packageName))
                    .forEach(clazz -> this.dumpClass(sender, clazz));

            try {
                val target = Class.forName(packageName);
                this.dumpClass(sender, target);
            } catch (ClassNotFoundException ignored) {
            }
        }), () -> sender.sendMessage("ClassLoader with hashcode " + classLoaderHashcode + " not found!"));
    }
}
