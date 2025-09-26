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
public class AnalysisClassCommand extends SubCommand {
    public AnalysisClassCommand() {
        super("analysisclass", null, "Show the full information of a class", "crackinghelper.command.main.analysisclass", false);
    }

    @Override
    public void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args) {
        if (args.length != 2) {
            sender.sendMessage("/crackinghelper analysisclass <classloader hashcode> <class name>");
            return;
        }

        val classLoaderHashCode = args[0];
        val className = args[1];

        val classLoader = ClassUtil.getClassLoaderByHashCode(classLoaderHashCode, IPluginController.getController().getInstrumentation());
        if (classLoader.isPresent()) {
            try {
                val targetClass = Class.forName(className, false, classLoader.get());
                sender.sendMessage("\n" + ClassUtil.getClassInfo(targetClass));
            } catch (ClassNotFoundException e) {
                sender.sendMessage("Class with name " + className + " not found!");
            }
        } else {
            sender.sendMessage("Class loader with hashCode " + classLoaderHashCode + " not found!");
        }
    }

    @Override
    public List<String> onTabComplete(CommonCommandSender<?> sender, String[] args) {
        if (args.length == 1) {
            return ClassUtil.getLoadedClassloaders(IPluginController.getController().getInstrumentation()).stream().map(classLoader -> Integer.toHexString(classLoader.hashCode())).toList();
        }

        if (args.length == 2) {
            val classLoader = ClassUtil.getClassLoaderByHashCode(args[0], IPluginController.getController().getInstrumentation());
            if (classLoader.isPresent()) {
                return ClassUtil.getAllClassesByClassLoader(classLoader.get(), IPluginController.getController().getInstrumentation()).values().stream().map(Class::getName).toList();
            }
        }

        return List.of();
    }
}
