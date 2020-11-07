package commands.music;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class PauseCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();

    @Override
    public void execute(Context context) {
        playerManager.setPaused(context.getEvent().getChannel(),true);
    }

    @Override
    public String getName() {
        return CommandName.PAUSE.toString();
    }

    @Override
    public String getUsage() {
        return "-pause";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Pause current track";
    }
}
