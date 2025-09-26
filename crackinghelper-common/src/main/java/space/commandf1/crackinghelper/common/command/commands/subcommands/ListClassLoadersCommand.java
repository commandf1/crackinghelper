package space.commandf1.crackinghelper.common.command.commands.subcommands;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.common.command.SubCommand;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;
import space.commandf1.crackinghelper.common.util.ClassUtil;

/**
 * @author commandf1
 */
public class ListClassLoadersCommand extends SubCommand {
    public ListClassLoadersCommand() {
        super("listclassloaders", null, "List all loaded classloaders", "crackinghelper.command.main.listclassloaders", false);
    }

    @Override
    public void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args) {
        val loadedClassloaders = ClassUtil.getLoadedClassloaders(IPluginController.getController().getInstrumentation());
        sender.sendMessage(String.format("Loaded classloaders(%s): ", loadedClassloaders.size()));
        loadedClassloaders.forEach(classLoader ->
                sender.sendMessage(this.getClassLoaderInfo(classLoader))
        );
    }

    private String getClassLoaderInfo(ClassLoader classLoader) {
        return String.format(
                "%s@%s extends %s@%s",
                classLoader.getClass().getName(),
                Integer.toHexString(classLoader.hashCode()),
                classLoader.getParent() == null ? "null" : classLoader.getParent().getClass().getName(),
                classLoader.getParent() == null ? "null" : Integer.toHexString(classLoader.getParent().hashCode())
        );
    }
}
