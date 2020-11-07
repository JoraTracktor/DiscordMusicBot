package commands;

import bot.Context;

public interface ICommand {

    void execute(Context context);

    String getName();

    String getUsage();

    String getArgs();

    String getHelp();
}
