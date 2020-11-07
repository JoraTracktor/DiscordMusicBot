package commands.useful;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import parser.WeatherParser;

public class WeatherCommand implements ICommand {

    private WeatherParser weatherParser = new WeatherParser();

    @Override
    public void execute(Context context) {
        context.getEvent().getChannel().sendMessage(weatherParser.getWeatherData(context.getArgs())).queue();
    }

    @Override
    public String getName() {
        return CommandName.WEATHER.toString();
    }

    @Override
    public String getUsage() {
        return "-weather moscow";
    }

    @Override
    public String getArgs() {
        return "[city]";
    }

    @Override
    public String getHelp() {
        return "Prints weather info in the city";
    }
}
