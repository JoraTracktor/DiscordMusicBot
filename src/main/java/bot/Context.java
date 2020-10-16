package bot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Context {
    private String args;
    private GuildMessageReceivedEvent event;

    public Context(String args, GuildMessageReceivedEvent event) {
        this.args = args;
        this.event = event;
    }

    public String getArgs() {
        return args;
    }

    public GuildMessageReceivedEvent getEvent() {
        return event;
    }
}
