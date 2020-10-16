package commands.music;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class NextTrackCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();
    @Override
    public boolean execute(Context context) {
        playerManager.playNext(context.getEvent().getChannel());
        return true;
    }

    @Override
    public String getName() {
        return CommandName.NEXT.toString();
    }

    @Override
    public String getUsage() {
        return "[-]";
    }

    @Override
    public String getHelp() {
        return "Playing next track in playlist";
    }
}
