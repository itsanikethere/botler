package com.github.itsanikethere.botler.player;

import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSourceBase;

public abstract class SongAudioSource extends AudioSourceBase {

    public SongAudioSource(DiscordApi api) {
        super(api);
    }

    public abstract void setHasFinished(boolean hasFinished);

}
