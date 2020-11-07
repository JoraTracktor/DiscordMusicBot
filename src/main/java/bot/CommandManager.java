package bot;

import commands.ICommand;
import commands.maths.CalculateCommand;
import commands.maths.ClearVarCommand;
import commands.maths.PrintVarCommand;
import commands.music.*;
import commands.useful.*;
import enums.CommandName;
import math.Math;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.HashMap;

import static enums.CommandName.*;

public class CommandManager {
    private static CommandManager INSTANCE;
    private final HashMap<CommandName, ICommand> commands = new HashMap<>();
    private final String PREFIX = "-";
    private String help;

    public static synchronized CommandManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommandManager();
        }
        return INSTANCE;
    }

    private CommandManager() {
        Connection connection = new Connection();
        Math math = new Math();
        commands.put(JOIN, new JoinCommand(connection));
        commands.put(LEAVE, new LeaveCommand(connection));
        commands.put(PLAY, new PlayCommand());
        commands.put(NEXT, new NextTrackCommand());
        commands.put(PAUSE, new PauseCommand());
        commands.put(CLEAR, new ClearCommand());
        commands.put(INFO, new InfoTrackCommand());
        commands.put(PLAYLIST, new InfoPlaylistCommand());

        commands.put(WEATHER, new WeatherCommand());
        commands.put(HELP, new HelpCommand());
        commands.put(UNKNOWN, new UnknownCommand());
        commands.put(PING, new PingCommand());

        commands.put(MATH, new CalculateCommand(math));
        commands.put(MCLEAR, new ClearVarCommand(math));
        commands.put(MPRINT, new PrintVarCommand(math));

//        help = String.format("%-10s %-10s %-20s %s\n", "Name","Args", "Use", "Info");
//        commands.forEach((k,v) -> help += String.format("%-10s %-10s %-20s %s\n", v.getName(), v.getArgs(), v.getUsage(), v.getHelp()));
        help = "https://raw.githubusercontent.com/JoraTracktor/DiscordMusicBot/master/README.md";
    }


    public void execute(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        String args = null;
        String[] split = message.getContentRaw().split("\\s", 2);

        CommandName commandName;

        if (!split[0].startsWith(PREFIX)) {
            return;
        }
        try {
            commandName = CommandName.valueOf(split[0].replaceFirst(PREFIX, "").toUpperCase());
        } catch (IllegalArgumentException e) {
            commandName = UNKNOWN;
        }
        if (split.length == 2) {
            args = split[1];
        }
        Context context = new Context(args, event);
        //System.out.println(commandName);
        executeCommand(commandName, context);
    }

    public void executeCommand(CommandName name, Context context) {
        commands.get(name).execute(context);
    }

    public String printHelp() {
        String message = "List of commands:\n";
        message += help;
        //System.out.println(message);
        return message;
    }
}
