package commands.music;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class InfoPlaylistCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();
    @Override
    public boolean execute(Context context) {
        playerManager.printPlayList(context.getEvent().getChannel());
        return true;
    }

    @Override
    public String getName() {
        return CommandName.PLAYLIST.toString();
    }

    @Override
    public String getUsage() {
        return "[-]";
    }

    @Override
    public String getHelp() {
        return "Print current playlist";
    }
}
