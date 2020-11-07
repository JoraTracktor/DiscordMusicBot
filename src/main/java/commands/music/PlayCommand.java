package commands.music;

import bot.CommandManager;
import bot.Context;
import commands.ICommand;
import enums.CommandName;
import player.PlayerManager;

public class PlayCommand implements ICommand {

    private PlayerManager playerManager = PlayerManager.getInstance();

    @Override
    public void execute(Context context) {

        CommandManager.getInstance().executeCommand(CommandName.JOIN, context);

        if (context.getArgs() == null){
            playerManager.setPaused(context.getEvent().getChannel(),false);
        } else {
            playerManager.addAndPlay(context.getEvent().getChannel(), context.getArgs());
        }
    }

    @Override
    public String getName() {
        return CommandName.PLAY.toString();
    }

    @Override
    public String getUsage() {
        return "-play\n-play https://www.youtube.com/watch?v=5yx6BWlEVcY&ab_channel=ChillhopMusic\n-play eminem rap god";
    }

    @Override
    public String getArgs() {
        return "[-|link|query]";
    }

    @Override
    public String getHelp() {
        return "Play track|play by link|play by query";
    }
}
