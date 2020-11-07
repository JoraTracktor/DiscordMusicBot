package commands.useful;

import bot.CommandManager;
import bot.Context;
import commands.ICommand;
import enums.CommandName;

public class HelpCommand implements ICommand {
    @Override
    public void execute(Context context) {
        String message = CommandManager.getInstance().printHelp();
        context.getEvent().getChannel().sendMessage(message).queue();
    }

    @Override
    public String getName() {
        return CommandName.HELP.toString();
    }

    @Override
    public String getUsage() {
        return "-help";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Print info about all commands";
    }
}
