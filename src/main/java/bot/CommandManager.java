package bot;

import commands.ICommand;
import commands.music.*;
import commands.useful.HelpCommand;
import enums.CommandName;
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
        commands.put(JOIN, new JoinCommand(connection));
        commands.put(LEAVE, new LeaveCommand(connection));
        commands.put(PLAY, new PlayCommand());
        commands.put(NEXT, new NextTrackCommand());
        commands.put(PAUSE, new PauseCommand());
        commands.put(ClEAR, new ClearCommand());
        commands.put(INFO, new InfoTrackCommand());
        commands.put(PLAYLIST, new InfoPlaylistCommand());

        commands.put(HELP, new HelpCommand());

        help = String.format("%-10s %-20s %s\n", "Name","Args", "Info");
        commands.forEach((k,v) -> help += String.format("%-10s %-20s %s\n", v.getName(), v.getUsage(), v.getHelp()));
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
        executeCommand(commandName, context);
    }

    public void executeCommand(CommandName name, Context context) {
        commands.get(name).execute(context);
    }

    public String printHelp() {
        String message = "List of commands:\n";
        message += help;
        System.out.println(message);
        return message;
    }
}
