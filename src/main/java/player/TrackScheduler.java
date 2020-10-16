package player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.TextChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> playList;
    private final TextChannel channel;

    public TrackScheduler(AudioPlayer player, TextChannel channel) {
        this.player = player;
        this.playList = new LinkedBlockingDeque<>();
        this.channel = channel;
    }

    public void addToPlayList(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            playList.offer(track);
        }
    }

    public void playNextTrack() {
        AudioTrack track = playList.poll();
        if( track == null){
            channel.sendMessage("Playlist is empty").queue();
            return;
        }
        channel.sendMessage("Starting play: " + track.getInfo().title).queue();
        player.startTrack(track, false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            playNextTrack();
        }
    }

    public void playPause(boolean enable){
        player.setPaused(enable);
    }

    public void clearQueue(){
        player.stopTrack();
        player.setPaused(false);
        playList.clear();
    }

    public AudioTrack getTrack(){
        return player.getPlayingTrack();
    }

    public BlockingQueue<AudioTrack> getPlayList() {
        return playList;
    }

}
