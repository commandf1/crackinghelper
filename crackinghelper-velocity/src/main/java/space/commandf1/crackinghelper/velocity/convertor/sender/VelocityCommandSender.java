package space.commandf1.crackinghelper.velocity.convertor.sender;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;

/**
 * @author commandf1
 */
public class VelocityCommandSender extends CommonCommandSender<CommandSource> {
    public VelocityCommandSender(CommandSource item) {
        super(item);
    }

    @Override
    public void sendMessage(String message) {
        this.getCommandSender().sendMessage(Component.text(message));
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
