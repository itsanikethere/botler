package com.github.itsanikethere.botler.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;

public class SongAudioSourceImpl extends SongAudioSource {

    private final AudioPlayer player;

    private AudioFrame lastFrame;
    private boolean hasFinished;

    public SongAudioSourceImpl(DiscordApi api, AudioPlayer player) {
        super(api);
        this.player = player;

        addAudioSourceFinishedListener(e -> {
            e.getConnection().close();
            hasFinished = false;
        });
    }

    @Override
    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    @Override
    public byte[] getNextFrame() {
        return lastFrame == null ? null : applyTransformers(lastFrame.getData());
    }

    @Override
    public boolean hasFinished() {
        return hasFinished;
    }

    @Override
    public boolean hasNextFrame() {
        lastFrame = player.provide();
        return lastFrame != null;
    }

    @Override
    public AudioSource copy() {
        return new SongAudioSourceImpl(getApi(), player);
    }

}
