package space.commandf1.crackinghelper.common.command.commands;

import space.commandf1.crackinghelper.common.command.CommandBase;
import space.commandf1.crackinghelper.common.command.commands.subcommands.*;

/**
 * @author commandf1
 */
public abstract class CrackingHelperCommand extends CommandBase {
    public CrackingHelperCommand() {
        super("cracking" + "helper",
                null,
                "Main command of CrackingHelper",
                "cracking" + "helper.command.main",
                false);
        this.registerSubCommand(new TrackClassesCommand());
        this.registerSubCommand(new DumpCommand());
        this.registerSubCommand(new ListClassesCommand());
        this.registerSubCommand(new LoadClassCommand());
        this.registerSubCommand(new AnalysisClassCommand());
        this.registerSubCommand(new PrintStackTraceCommand());
        this.registerSubCommand(new ListClassLoadersCommand());
        this.registerSubCommand(new DecompileCommand());
    }
}
