package commands.useful;

import bot.Context;
import commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class WeatherCommand implements ICommand {
    @Override
    public boolean execute(Context context) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getHelp() {
        return "print weather info in the city";
    }
}