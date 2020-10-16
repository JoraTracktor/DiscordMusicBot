package commands.music;

import bot.Connection;
import bot.Context;
import commands.ICommand;
import enums.CommandName;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.List;

public class JoinCommand implements ICommand {

    private Connection connection;
    public JoinCommand(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean execute(Context context) {
        connection.connectToChannel(context.getEvent());
        return true;
    }

    @Override
    public String getName() {
        return CommandName.JOIN.toString();
    }

    @Override
    public String getUsage() {
        return "[-]";
    }

    @Override
    public String getHelp() {
        return "Join bot to a channel";
    }
}
