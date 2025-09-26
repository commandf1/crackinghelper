package space.commandf1.crackinghelper.bukkit.command;

import lombok.val;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.bukkit.BukkitCrackingHelperPlugin;
import space.commandf1.crackinghelper.bukkit.convertor.sender.BukkitCommandSender;
import space.commandf1.crackinghelper.common.command.commands.CrackingHelperCommand;

import java.util.List;

/**
 * @author commandf1
 */
public class BukkitCrackingHelperCommand extends CrackingHelperCommand
        implements CommandExecutor, TabCompleter {
    @Override
    public final boolean onCommand(@NotNull CommandSender sender,
                                   @NotNull Command command,
                                   @NotNull String label,
                                   @NotNull String[] args) {
        return super.onCommand(new BukkitCommandSender(sender), args);
    }

    @Override
    public final void register() {
        val command = BukkitCrackingHelperPlugin.getInstance().getCommand(this.getName());
        val aliases = this.getAliases();

        if (aliases != null) {
            command.setAliases(List.of(aliases));
        }

        val description = this.getDescription();
        if (description != null) {
            command.setDescription(description);
        }

        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return super.onTabComplete(new BukkitCommandSender(commandSender), strings);
    }
}
