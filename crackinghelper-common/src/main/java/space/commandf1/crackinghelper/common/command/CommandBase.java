package space.commandf1.crackinghelper.common.command;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.commandf1.crackinghelper.common.convertor.command.ICommandExecutor;
import space.commandf1.crackinghelper.common.convertor.command.ITabCompleter;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;

import java.util.*;

/**
 * @author commandf1
 */
@EqualsAndHashCode
@ToString
public abstract class CommandBase implements ICommandExecutor, ITabCompleter {
    @Getter
    private final @NotNull String name;

    @Getter
    private final @Nullable String[] aliases;

    @Getter
    private final @Nullable String description;

    @Getter
    private final @Nullable String permission;

    @Getter
    @Setter
    private @Nullable String noPermissionMessage;

    @Getter
    @Setter
    private @Nullable String unknownSubCommandMessage;

    @Getter
    private final boolean playerOnly;

    protected final @NotNull Map<String, SubCommand> subCommands = new HashMap<>();

    public CommandBase(@NotNull String name) {
        this(name, null, null, null, false);
    }

    public CommandBase(@NotNull String name,
                       @Nullable String[] aliases,
                       @Nullable String description,
                       @Nullable String permission,
                       boolean playerOnly) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
        this.playerOnly = playerOnly;
    }

    public abstract void register();

    public final void registerSubCommand(@NotNull SubCommand subCommand) {
        this.subCommands.put(subCommand.getName().toLowerCase(), subCommand);
    }

    @Override
    public boolean onCommand(CommonCommandSender<?> sender, String[] args) {
        val permission = this.getPermission();
        if (permission != null && !sender.hasPermission(permission)) {
            val noPermissionMessage = this.getNoPermissionMessage();
            if (noPermissionMessage != null) {
                sender.sendMessage(noPermissionMessage);
            }
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(this.getHelpMessage(sender));
            return true;
        }

        val subCommandName = args[0].toLowerCase();
        val subCommand = this.subCommands.get(subCommandName);

        if (subCommand == null) {
            val unknownSubCommandMessage = getUnknownSubCommandMessage();
            if (unknownSubCommandMessage != null) {
                sender.sendMessage(unknownSubCommandMessage);
            }
            return true;
        }

        val subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.execute(sender, subCommandArgs);

        return true;
    }

    public @NotNull String getHelpMessage(CommonCommandSender<?> sender) {
        boolean isPlayer = sender.isPlayer();
        val availableSubCommands = this.subCommands.values().stream()
                .filter(Objects::nonNull)
                .filter(subCommand ->
                        subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())
                )
                .filter(subCommand ->
                        !subCommand.isPlayerOnly() || isPlayer
                )
                .toList();

        val stringBuilder = new StringBuilder()
                .append("Available commands(")
                .append(availableSubCommands.size())
                .append("): ")
                .append("\n");

        val iterator = availableSubCommands.iterator();
        while (iterator.hasNext()) {
            val subCommand = iterator.next();
            stringBuilder.append(subCommand.getName());

            if (subCommand.getAliases() != null && subCommand.getAliases().length > 0) {
                stringBuilder.append(' ')
                        .append(Arrays.toString(subCommand.getAliases()));
            }

            val description = subCommand.getDescription();
            if (description != null && !description.isEmpty()) {
                stringBuilder.append(" - ").append(description);
            }

            if (iterator.hasNext()) {
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public List<String> onTabComplete(CommonCommandSender<?> sender, String[] args) {
        if (args.length == 1) {
            return this.subCommands.values().stream()
                    .filter(Objects::nonNull)
                    .filter(subCommand ->
                            subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())
                    )
                    .filter(subCommand ->
                            !subCommand.isPlayerOnly() || sender.isPlayer()
                    )
                    .map(SubCommand::getName)
                    .toList();
        } else if (args.length > 1) {
            String subCommandName = args[0].toLowerCase();
            val subCommand = this.subCommands.get(subCommandName);
            if (subCommand == null) {
                return List.of();
            }
            return subCommand.onTabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        return List.of();
    }
}
