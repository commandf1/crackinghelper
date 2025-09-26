package space.commandf1.crackinghelper.common.convertor.command;

import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;

/**
 * @author commandf1
 */
public interface ICommandExecutor {
    boolean onCommand(CommonCommandSender<?> sender, String[] args);
}
