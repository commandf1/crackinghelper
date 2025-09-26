package space.commandf1.crackinghelper.common.command.commands.subcommands;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import space.commandf1.crackinghelper.common.command.SubCommand;
import space.commandf1.crackinghelper.common.convertor.sender.CommonCommandSender;
import space.commandf1.crackinghelper.common.tracker.TrackerManager;
import space.commandf1.crackinghelper.common.tracker.trackers.MethodTracker;

/**
 * @author commandf1
 */
public class TrackClassesCommand extends SubCommand {
    public TrackClassesCommand() {
        super("track" + "class", null, "Track a method", "crackinghelper.command.main.trackclass", false);
    }

    @Override
    public void execute(@NotNull CommonCommandSender<?> sender, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You have to provide a method name.");
            sender.sendMessage("/crackinghelper trackclass <method name>");
            sender.sendMessage("Example: /crackinghelper trackclass org.bukkit.Bukkit#broadcastMessage");
            return;
        }

        val methodName = args[0];
        if (!methodName.contains("#")) {
            sender.sendMessage("You have to provide a method name.");
            sender.sendMessage("/crackinghelper trackclass <method name>");
            sender.sendMessage("Example: /crackinghelper trackclass org.bukkit.Bukkit#broadcastMessage");
            return;
        }
        TrackerManager.getManager().register(new MethodTracker(), methodName);
    }
}
