package commands.useful;

import bot.CommandManager;
import bot.Context;
import commands.ICommand;
import enums.CommandName;


public class UnknownCommand implements ICommand {
    @Override
    public boolean execute(Context context) {
        context.getEvent().getChannel().sendMessage(getHelp()).queue();
        CommandManager.getInstance().executeCommand(CommandName.HELP, context);
        return true;
    }

    @Override
    public String getName() {
        return CommandName.UNKNOWN.toString();
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getHelp() {
        return "Unknown command";
    }
}
