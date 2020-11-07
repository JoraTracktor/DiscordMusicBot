package commands.music;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class NextTrackCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();
    @Override
    public void execute(Context context) {
        playerManager.playNext(context.getEvent().getChannel());
    }

    @Override
    public String getName() {
        return CommandName.NEXT.toString();
    }

    @Override
    public String getUsage() {
        return "-next";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Playing next track in playlist";
    }
}
