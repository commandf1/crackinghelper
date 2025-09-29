package space.commandf1.crackinghelper.common.command.commands.subcommands;

import lombok.val;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.StubMethod;
import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.common.command.SubCommand;
import space.commandf1.crackinghelper.common.convertor.plugin.IPluginController;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;

import static net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.*;
import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * @author commandf1
 */
public class BlockMethodCommand extends SubCommand {
    public BlockMethodCommand() {
        super("blockmethod", null, "Block a method against being invoked", "crackinghelper.command.main.blockmethod", false);
    }

    @Override
    public void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You have to provide a method name.");
            sender.sendMessage("/crackinghelper blockmethod <method name>");
            sender.sendMessage("Example: /crackinghelper blockmethod org.bukkit.Bukkit#broadcastMessage");
            return;
        }

        val rawMethodName = args[0];
        if (!rawMethodName.contains("#")) {
            sender.sendMessage("You have to provide a method name.");
            sender.sendMessage("/crackinghelper blockmethod <method name>");
            sender.sendMessage("Example: /crackinghelper blockmethod org.bukkit.Bukkit#broadcastMessage");
            return;
        }

        val split = rawMethodName.split("#");

        val className = split[0];
        val methodName = split[1];

        new AgentBuilder.Default()
                .with(RETRANSFORMATION)
                .type(named(className))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    if (classLoader != null) {
                        sender.sendMessage("Blocking method " + methodName + " in class " + className + " of classloader " + classLoader);
                    } else {
                        sender.sendMessage("Blocking method " + methodName + " in class " + className);
                    }
                    return builder.method(named(methodName)).intercept(StubMethod.INSTANCE);
                })
                .installOn(IPluginController.getController().getInstrumentation());
        sender.sendMessage("Blocked method " + methodName + " in class " + className);
    }
}
