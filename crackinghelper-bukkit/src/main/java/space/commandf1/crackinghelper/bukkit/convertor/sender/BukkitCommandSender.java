package space.commandf1.crackinghelper.bukkit.convertor.sender;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;

/**
 * @author commandf1
 */
public class BukkitCommandSender extends CommonCommandSender<CommandSender> {
    public BukkitCommandSender(CommandSender sender) {
        super(sender);
    }

    @Override
    public void sendMessage(String message) {
        this.getCommandSender().sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.getCommandSender().hasPermission(permission);
    }

    @Override
    public boolean isPlayer() {
        return this.getCommandSender() instanceof Player;
    }
}
