package commands.music;

import bot.Connection;
import bot.Context;
import commands.ICommand;
import enums.CommandName;

public class JoinCommand implements ICommand {

    private Connection connection;
    public JoinCommand(Connection connection){
        this.connection = connection;
    }

    @Override
    public void execute(Context context) {
        connection.connectToChannel(context.getEvent());
    }

    @Override
    public String getName() {
        return CommandName.JOIN.toString();
    }

    @Override
    public String getUsage() {
        return "-join";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Join bot to a channel";
    }
}
