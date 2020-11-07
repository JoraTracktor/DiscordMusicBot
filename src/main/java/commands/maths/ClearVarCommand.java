package commands.maths;

import bot.Context;
import commands.ICommand;
import enums.CommandName;
import math.Math;

public class ClearVarCommand implements ICommand {

    private Math math;

    public ClearVarCommand(Math math){
        this.math = math;
    }

    @Override
    public void execute(Context context) {
        math.clearVars(context);
        context.getEvent().getChannel().sendMessage("Variables are deleted!").queue();
    }

    @Override
    public String getName() {
        return CommandName.MCLEAR.toString();
    }

    @Override
    public String getUsage() {
        return "-mclear";
    }

    @Override
    public String getArgs() {
        return "[no args]";
    }

    @Override
    public String getHelp() {
        return "Deleting saved variables in math";
    }
}
