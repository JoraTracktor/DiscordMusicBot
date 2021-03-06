package commands.useful;

import bot.CommandManager;
import bot.Context;
import commands.ICommand;
import enums.CommandName;

public class UnknownCommand implements ICommand {
    @Override
    public void execute(Context context) {
        context.getEvent().getChannel().sendMessage(getHelp()).queue();
        CommandManager.getInstance().executeCommand(CommandName.HELP, context);
    }

    @Override
    public String getName() {
        return CommandName.UNKNOWN.toString();
    }

    @Override
    public String getUsage() {
        return "Did not use";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "no such command exists";
    }
}
