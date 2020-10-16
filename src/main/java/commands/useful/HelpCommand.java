package commands.useful;

import bot.CommandManager;
import bot.Context;
import commands.ICommand;
import enums.CommandName;

public class HelpCommand implements ICommand {
    @Override
    public boolean execute(Context context) {
        String message = CommandManager.getInstance().printHelp();
        context.getEvent().getChannel().sendMessage(message).queue();
        return true;
    }

    @Override
    public String getName() {
        return CommandName.HELP.toString();
    }

    @Override
    public String getUsage() {
        return "[-]";
    }

    @Override
    public String getHelp() {
        return "Print info about all commands";
    }
}
