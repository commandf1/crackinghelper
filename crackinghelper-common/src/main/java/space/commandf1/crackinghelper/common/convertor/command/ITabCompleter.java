package space.commandf1.crackinghelper.common.convertor.command;

import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;

import java.util.List;

/**
 * @author commandf1
 */
public interface ITabCompleter {
    List<String> onTabComplete(CommonCommandSender<?> sender, String[] args);
}
