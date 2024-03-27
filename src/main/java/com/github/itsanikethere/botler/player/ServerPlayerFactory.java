package com.github.itsanikethere.botler.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;

import org.javacord.api.DiscordApi;

import java.util.HashMap;
import java.util.Map;

public class ServerPlayerFactory {

    private final static AudioPlayerManager PLAYER_MANAGER = new DefaultAudioPlayerManager();
    private final static Map<Long, ServerPlayer> SERVER_PLAYERS = new HashMap<>();

    static {
        PLAYER_MANAGER.registerSourceManager(new YoutubeAudioSourceManager());
    }

    private ServerPlayerFactory() {
    }

    public static synchronized ServerPlayer get(long serverId, DiscordApi api) {
        return SERVER_PLAYERS.computeIfAbsent(serverId, mappingFunction ->
                new ServerPlayer(api, PLAYER_MANAGER.createPlayer(), PLAYER_MANAGER)
        );
    }

}
