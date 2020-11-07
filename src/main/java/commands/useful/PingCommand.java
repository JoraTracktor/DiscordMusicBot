package commands.useful;

import bot.Context;
import commands.ICommand;
import enums.CommandName;

public class PingCommand implements ICommand {
    @Override
    public void execute(Context context) {
        long time = System.currentTimeMillis();
        context.getEvent().getChannel().sendMessage("Ping: ").queue(response -> {
                    response.editMessageFormat("Ping: %d ms", System.currentTimeMillis() - time).queue();
        });
    }

    @Override
    public String getName() {
        return CommandName.PING.toString();
    }

    @Override
    public String getUsage() {
        return "-ping";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Prints ping";
    }
}
