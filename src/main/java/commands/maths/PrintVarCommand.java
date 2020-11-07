package commands.maths;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import math.Math;

public class PrintVarCommand implements ICommand {

    private Math math;

    public PrintVarCommand(Math math){
        this.math = math;
    }

    @Override
    public void execute(Context context) {
        context.getEvent().getChannel().sendMessage(math.printVars(context)).queue();
    }

    @Override
    public String getName() {
        return CommandName.MPRINT.toString();
    }

    @Override
    public String getUsage() {
        return "-mprint";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Prints all saved variables";
    }
}
