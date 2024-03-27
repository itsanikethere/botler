package com.github.itsanikethere.botler.player;

import com.github.itsanikethere.botler.constant.ErrorMessageConstants;
import com.github.itsanikethere.botler.dto.Song;
import com.github.itsanikethere.botler.helper.SongMessageSender;
import com.github.itsanikethere.botler.helper.YoutubeHelper;
import com.github.itsanikethere.botler.util.ErrorMessageSender;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;

import java.util.List;
import java.util.concurrent.Semaphore;

public class ServerPlayer {

    public static final long DEFAULT_SEEK_SECONDS = 10;

    private final DiscordApi api;
    private final AudioPlayer player;
    private final AudioPlayerManager playerManager;
    private final Semaphore songControlLock;
    private final SongAudioSource songAudioSource;
    private final SongScheduler songScheduler;

    private Message nowPlayingMessage;

    public ServerPlayer(DiscordApi api, AudioPlayer player, AudioPlayerManager playerManager) {
        this.api = api;
        this.player = player;
        this.playerManager = playerManager;
        songControlLock = new Semaphore(1);
        songAudioSource = new SongAudioSourceImpl(api, player);
        songScheduler = new SongScheduler(player, this);
    }

    public SongAudioSource getSongAudioSource() {
        return songAudioSource;
    }

    public boolean isNotPlaying() {
        return player.getPlayingTrack() == null;
    }

    public synchronized void play(String query, User requestingUser, TextChannel requestChannel,
                                  InteractionFollowupMessageBuilder responder) {
        playerManager.loadItemOrdered(this, YoutubeHelper.preprocessQuery(query), new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                queueSong(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                List<AudioTrack> tracks = playlist.getTracks();

                if (playlist.isSearchResult()) {
                    queueSong(tracks.get(0));
                    return;
                }

                queuePlaylist(tracks, playlist.getName());
            }

            @Override
            public void noMatches() {
                ErrorMessageSender.send(ErrorMessageConstants.NO_RESULT_FOUND, responder);
            }

            @Override
            public void loadFailed(FriendlyException e) {
                String message = e.getMessage();
                ErrorMessageSender.send("**" + (message.endsWith(".") ? message : message + ".") + "**", responder);
            }

            private void queueSong(AudioTrack track) {
                Song song = new Song(requestingUser, requestChannel, track, track.getInfo());
                SongMessageSender.sendSongQueueMessage(song, songScheduler.queue(song), responder);
            }

            private void queuePlaylist(List<AudioTrack> tracks, String name) {
                tracks
                        .stream()
                        .map(track -> new Song(requestingUser, requestChannel, track, track.getInfo()))
                        .forEach(songScheduler::queue);
                SongMessageSender.sendPlaylistQueueMessage(tracks.size(), name, responder);
            }
        });
    }

    public boolean acquireSongControl(Interaction interaction) {
        try {
            songControlLock.acquire();

        } catch (InterruptedException ignored) {
            ErrorMessageSender.send(ErrorMessageConstants.GENERIC, interaction.createFollowupMessageBuilder());
            songControlLock.release();
            return false;
        }

        if (isNotPlaying()) {
            ErrorMessageSender.send(ErrorMessageConstants.NO_SONG_PLAYING, interaction.createFollowupMessageBuilder());
            songControlLock.release();
            return false;
        }

        return true;
    }

    public void pauseOrResume(User requestingUser, InteractionFollowupMessageBuilder responder) {
        SongMessageSender.sendPlayerPauseOrResumeMessage(requestingUser, player.isPaused(), responder);
        player.setPaused(!player.isPaused());
        songControlLock.release();
    }

    public void skip(User requestingUser, InteractionFollowupMessageBuilder responder) {
        SongMessageSender.sendSongSkipMessage(requestingUser, player.getPlayingTrack().getInfo(), responder);
        player.stopTrack();
        player.setPaused(false);
        songControlLock.release();
    }

    public void stop(User requestingUser, InteractionFollowupMessageBuilder responder) {
        SongMessageSender.sendPlayerStopMessage(requestingUser, responder);
        songScheduler.clear();
        player.stopTrack();
        songControlLock.release();
    }

    public void replay(User requestingUser, InteractionFollowupMessageBuilder responder) {
        SongMessageSender.sendSongReplayMessage(requestingUser, responder);
        player
                .getPlayingTrack()
                .setPosition(1);
        player.setPaused(false);
        songControlLock.release();
    }

    public void forward(User requestingUser, long seconds, InteractionFollowupMessageBuilder responder) {
        AudioTrack track = player.getPlayingTrack();

        long newPosition = track.getPosition() + (seconds * 1000);

        if (newPosition > track.getDuration()) {
            ErrorMessageSender.send(ErrorMessageConstants.CANNOT_FAST_FORWARD_BEYOND_END, responder);
            songControlLock.release();
            return;
        }

        SongMessageSender.sendPlayerFastForwardMessage(requestingUser, seconds, responder);
        track.setPosition(newPosition);
        songControlLock.release();
    }

    public void rewind(User requestingUser, long seconds, InteractionFollowupMessageBuilder responder) {
        AudioTrack track = player.getPlayingTrack();

        long newPosition = track.getPosition() - (seconds * 1000);

        if (newPosition < 0) {
            ErrorMessageSender.send(ErrorMessageConstants.CANNOT_REWIND_BEFORE_START, responder);
            songControlLock.release();
            return;
        }

        SongMessageSender.sendPlayerRewindMessage(requestingUser, seconds, responder);
        track.setPosition(newPosition);
        songControlLock.release();
    }

    public void sendNowPlayingMessage(Song song) {
        SongMessageSender
                .sendNowPlayingMessage(song, api.getYourself())
                .thenAccept(message -> nowPlayingMessage = message);
    }

    public void deleteNowPlayingMessage() {
        if (nowPlayingMessage != null) {
            Message.delete(api, nowPlayingMessage);
        }
    }

    public void dispose() {
        songAudioSource.setHasFinished(true);
        player.setPaused(false);
    }

}
