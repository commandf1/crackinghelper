package space.commandf1.crackinghelper.common.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.commandf1.crackinghelper.common.convertor.command.ITabCompleter;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;

import java.util.List;

/**
 * @author commandf1
 */
@EqualsAndHashCode
@ToString
public abstract class SubCommand implements ITabCompleter {
    @Getter
    private final @NotNull String name;

    @Getter
    private final @Nullable String[] aliases;

    @Getter
    private final @Nullable String description;

    @Getter
    private final @Nullable String permission;

    @Getter
    private final boolean playerOnly;

    public SubCommand(@NotNull String name) {
        this(name, null, null, null, false);
    }

    public SubCommand(@NotNull String name,
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

    public abstract void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args);

    @Override
    public List<String> onTabComplete(CommonCommandSender<?> sender, String[] args) {
        return List.of();
    }
}
