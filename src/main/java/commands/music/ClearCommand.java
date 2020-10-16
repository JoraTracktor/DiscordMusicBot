package commands.music;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class ClearCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();
    @Override
    public boolean execute(Context context) {
        playerManager.clearQueue(context.getEvent().getChannel());
        return true;
    }

    @Override
    public String getName() {
        return CommandName.ClEAR.toString();
    }

    @Override
    public String getUsage() {
        return "[-]";
    }

    @Override
    public String getHelp() {
        return "Stop play current track and clear queue";
    }
}
