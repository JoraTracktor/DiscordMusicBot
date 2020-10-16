package commands;

import bot.Context;

public interface ICommand {

    boolean execute(Context context);

    String getName();

    String getUsage();

    String getHelp();
}
