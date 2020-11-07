package commands.music;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class ClearCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();
    @Override
    public void execute(Context context) {
        playerManager.clearQueue(context.getEvent().getChannel());
    }

    @Override
    public String getName() {
        return CommandName.CLEAR.toString();
    }

    @Override
    public String getUsage() {
        return "-stop";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Stop playing current track and clear queue";
    }
}
