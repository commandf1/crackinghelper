package space.commandf1.crackinghelper.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import lombok.val;
import space.commandf1.crackinghelper.common.command.commands.CrackingHelperCommand;
import space.commandf1.crackinghelper.velocity.VelocityCrackingHelperPlugin;
import space.commandf1.crackinghelper.velocity.convertor.sender.VelocityCommandSender;

import java.util.List;

/**
 * @author commandf1
 */
public class VelocityCrackingHelperCommand extends CrackingHelperCommand implements SimpleCommand {
    @Override
    public void register() {
        val commandManager = VelocityCrackingHelperPlugin.getInstance().getServer().getCommandManager();
        val meta = commandManager
                .metaBuilder(this.getName())
                .aliases(this.getAliases() == null ? new String[0] : this.getAliases())
                .build();
        commandManager.register(meta, this);
    }

    @Override
    public void execute(Invocation invocation) {
        super.onCommand(new VelocityCommandSender(invocation.source()), invocation.arguments());
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return super.onTabComplete(new VelocityCommandSender(invocation.source()), invocation.arguments());
    }
}
