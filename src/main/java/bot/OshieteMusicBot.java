package bot;

import enums.CommandName;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class OshieteMusicBot extends ListenerAdapter{

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        File file = new File(ClassLoader.getSystemResource("data.properties").toURI());
        properties.load(new FileReader(file));
        String token = properties.getProperty("token");

        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new OshieteMusicBot());
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        CommandManager commandManager = CommandManager.getInstance();
        commandManager.execute(event);
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        List<TextChannel> channels = event.getGuild().getTextChannels();
        channels.forEach(channel -> channel.sendMessageFormat("Hi all! **%s**", event.getGuild().getName()).queue());
        CommandManager commandManager = CommandManager.getInstance();
        //commandManager.executeCommand(CommandName.HELP, null, null);
    }
}

