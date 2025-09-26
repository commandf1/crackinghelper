package space.commandf1.crackinghelper.common.command.commands.subcommands;

import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.common.command.SubCommand;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;
import space.commandf1.crackinghelper.common.util.InterceptionUtil;

/**
 * @author commandf1
 */
public class PrintStackTraceCommand extends SubCommand {
    public PrintStackTraceCommand() {
        super("printstacktrace", null, "Prints the stack trace", "crackinghelper.command.main.printstacktrace", false);
    }

    @Override
    public void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args) {
        StringBuilder builder = new StringBuilder("\n");

        InterceptionUtil.STACK_WALKER.forEach(frame -> {
            builder.append(String.format("%s#%s(%s:%s)",
                    frame.getClassName(),
                    frame.getMethodName(),
                    frame.getFileName(),
                    frame.getLineNumber()
            ));

            builder.append("\n");
        });

        sender.sendMessage(builder.toString());
    }
}
