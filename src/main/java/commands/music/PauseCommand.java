package commands.music;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class PauseCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();

    @Override
    public boolean execute(Context context) {
        playerManager.setPaused(context.getEvent().getChannel(),true);
        return true;
    }

    @Override
    public String getName() {
        return CommandName.PAUSE.toString();
    }

    @Override
    public String getUsage() {
        return "[-]";
    }

    @Override
    public String getHelp() {
        return "Pause current track";
    }
}
