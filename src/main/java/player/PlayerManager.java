package player;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    private PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(TextChannel channel) {
        Guild guild = channel.getGuild();
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager, channel);
            musicManagers.put(guildId, musicManager);
        }
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    public void addAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = getGuildMusicManager(channel);
        String search = trackUrl;
        if (!isUrl(trackUrl)) {
            trackUrl = "ytsearch:" + trackUrl;
        }
        System.out.println(trackUrl);
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding to playlist: " + track.getInfo().title).queue();
                musicManager.scheduler.addToPlayList(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                List<AudioTrack> tracks = playlist.getTracks();
                for (AudioTrack track : tracks) {
                    System.out.println(track.getInfo().title);
                    musicManager.scheduler.addToPlayList(track);
                }
                channel.sendMessage("Adding to playlist: ")
                        .append(String.valueOf(tracks.size() - 1))
                        .append(" tracks from ")
                        .append(search)
                        .queue();
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by:" + search).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    public void playNext(TextChannel channel) {
        GuildMusicManager musicManager = getGuildMusicManager(channel);
        musicManager.scheduler.playNextTrack();
    }

    public void clearQueue(TextChannel channel){
        GuildMusicManager musicManager = getGuildMusicManager(channel);
        channel.sendMessage("Delete " + musicManager.scheduler.getPlayList().size() + " tracks from playlist").queue();
        musicManager.scheduler.clearQueue();

    }
    public void setPaused(TextChannel channel, boolean pause){
        GuildMusicManager musicManager = getGuildMusicManager(channel);
        musicManager.scheduler.playPause(pause);
    }


    public void printPlayList(TextChannel channel){
        GuildMusicManager musicManager = getGuildMusicManager(channel);
        List<AudioTrack> list = new ArrayList<>(musicManager.scheduler.getPlayList());

        if (list.isEmpty()){
            channel.sendMessage("Current playlist is empty").queue();
            return;
        }
        StringBuilder message = new StringBuilder("Total tracks: " + list.size() + "\n");

        for (int index = 1; index <= list.size(); index++){
            message.append(index).append(". ").append(list.get(index - 1).getInfo().title).append("\n");
        }
        channel.sendMessage(message.toString()).queue();
    }

    public void printTrackInfo(TextChannel channel){
        GuildMusicManager musicManager = getGuildMusicManager(channel);
        AudioTrack track = musicManager.scheduler.getTrack();

        StringBuilder message = new StringBuilder("Current track: \n");
        message.append("Tittle: ").append(track.getInfo().title).append("\n");
        message.append("Author: ").append(track.getInfo().author).append("\n");
        message.append("Duration: ").append(toMinutes(track.getInfo().length)).append("\n");
        message.append("Link: ").append(track.getInfo().uri);
        channel.sendMessage(message).queue();
    }
    private String toMinutes(long millis){
        long minutes = (millis / 1000)  / 60;
        long seconds = (millis / 1000) % 60;
        return "" + minutes + "." + seconds;
    }

    private boolean isUrl(String url){
        URL u;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }

        try {
            u.toURI();
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }
}