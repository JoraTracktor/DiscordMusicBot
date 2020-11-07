package commands.music;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class InfoTrackCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();
    @Override
    public void execute(Context context) {
        playerManager.printTrackInfo(context.getEvent().getChannel());
    }

    @Override
    public String getName() {
        return CommandName.INFO.toString();
    }

    @Override
    public String getUsage() {
        return "-info";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Print info about current track";
    }
}
