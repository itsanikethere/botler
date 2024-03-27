package com.github.itsanikethere.botler.player;

import com.github.itsanikethere.botler.dto.Song;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class SongScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final ServerPlayer serverPlayer;
    private final Queue<Song> songQueue;

    SongScheduler(AudioPlayer player, ServerPlayer serverPlayer) {
        this.player = player;
        this.serverPlayer = serverPlayer;
        songQueue = new LinkedBlockingQueue<>();

        player.addListener(this);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        serverPlayer.deleteNowPlayingMessage();

        if (songQueue.isEmpty()) {
            serverPlayer.dispose();
            return;
        }

        Song song = songQueue.poll();

        player.startTrack(song.track(), false);
        serverPlayer.sendNowPlayingMessage(song);
    }

    public int queue(Song song) {
        if (player.startTrack(song.track(), true)) {
            serverPlayer.sendNowPlayingMessage(song);
            return 1;
        }

        songQueue.offer(song);
        return songQueue.size();
    }

    public void clear() {
        songQueue.clear();
    }

}
