package space.commandf1.crackinghelper.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import space.commandf1.crackinghelper.bungee.BungeeCrackingHelperPlugin;
import space.commandf1.crackinghelper.bungee.convertor.sender.BungeeCommandSender;
import space.commandf1.crackinghelper.common.command.commands.CrackingHelperCommand;

/**
 * @author commandf1
 */
public class BungeeCrackingHelperCommand extends CrackingHelperCommand {
    @Override
    public void register() {
        ProxyServer.getInstance().getPluginManager().registerCommand(
                BungeeCrackingHelperPlugin.getInstance(),
                new BungeeCommand(this)
        );
    }

    private static class BungeeCommand extends Command implements TabExecutor {
        private final BungeeCrackingHelperCommand command;

        public BungeeCommand(BungeeCrackingHelperCommand command) {
            super(command.getName(), command.getPermission(), command.getAliases());
            this.command = command;
            this.setPermissionMessage(this.command.getNoPermissionMessage());
        }

        @Override
        public void execute(CommandSender commandSender, String[] strings) {
            this.command.onCommand(new BungeeCommandSender(commandSender), strings);
        }

        @Override
        public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
            return this.command.onTabComplete(new BungeeCommandSender(sender), args);
        }
    }
}
