package commands.maths;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import math.Math;
public class CalculateCommand implements ICommand {

    private Math math;

    public CalculateCommand(Math math){
        this.math = math;
    }

    @Override
    public void execute(Context context) {

        context.getEvent().getChannel().sendMessage(math.calculate(context.getArgs())).queue();
    }

    @Override
    public String getName() {
        return CommandName.MATH.toString();
    }

    @Override
    public String getUsage() {
        return "-math 23 + 76 * 2\n-math var = 33";
    }

    @Override
    public String getArgs() {
        return "[math expr]\n[var = math expr]";
    }

    @Override
    public String getHelp() {
        return "calculate math expression";
    }
}
