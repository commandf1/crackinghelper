package space.commandf1.crackinghelper.common.command.commands.subcommands;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.common.command.SubCommand;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;
import space.commandf1.crackinghelper.common.util.ClassUtil;

import java.util.List;

/**
 * @author commandf1
 */
public class ListClassesCommand extends SubCommand {
    public ListClassesCommand() {
        super("listclasses", null, "List all classes loaded in a plugin", "crackinghelper.command.main.listclasses", false);
    }

    @Override
    public List<String> onTabComplete(CommonCommandSender<?> sender, String[] args) {
        if (args.length == 1) {
            return ClassUtil.getLoadedClassloaders(IPluginController.getController().getInstrumentation()).stream().map(classLoader -> Integer.toHexString(classLoader.hashCode())).toList();
        }

        return List.of();
    }

    @Override
    public void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage("/crackinghelper listclasses <classloader hashcode>");
            return;
        }

        val classloaderHashcode = args[0];

        val instrumentation = IPluginController.getController().getInstrumentation();
        val classLoader = ClassUtil.getClassLoaderByHashCode(classloaderHashcode, instrumentation);
        classLoader.ifPresentOrElse(loader -> {
            val classes = ClassUtil.getAllClassesByClassLoader(loader, instrumentation);
            sender.sendMessage(String.format("Loaded classes(%s): ", classes.size()));
            classes.forEach((name, clazz) -> sender.sendMessage(name));
        }, () -> sender.sendMessage("Classloader with hashcode " + classloaderHashcode + " not found"));
    }
}
