package space.commandf1.crackinghelper.common.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author commandf1
 */
public class CommandManager {
    private static CommandManager manager;
    private final Map<String, CommandBase> COMMANDS = new HashMap<>();

    public static CommandManager getManager() {
        return manager == null ? manager = new CommandManager() : manager;
    }

    public void registerCommand(CommandBase command) {
        COMMANDS.put(command.getName(), command);
        command.register();
    }

    public void registerCommands(CommandBase... commands) {
        Arrays.stream(commands).forEach(this::registerCommand);
    }

    public CommandBase getCommand(String name) {
        return COMMANDS.get(name);
    }
}
