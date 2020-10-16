package commands.music;

import bot.Connection;
import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class LeaveCommand implements ICommand {

    private Connection connection;
    private PlayerManager playerManager = PlayerManager.getInstance();

    public LeaveCommand(Connection connection){
        this.connection = connection;
    }
    @Override
    public boolean execute(Context context) {
        playerManager.clearQueue(context.getEvent().getChannel());
        connection.disconnectFromChannel(context.getEvent());
        return true;
    }

    @Override
    public String getName() {
        return CommandName.LEAVE.toString();
    }

    @Override
    public String getUsage() {
        return "[-]";
    }

    @Override
    public String getHelp() {
        return "Shutdown a bot";
    }
}
