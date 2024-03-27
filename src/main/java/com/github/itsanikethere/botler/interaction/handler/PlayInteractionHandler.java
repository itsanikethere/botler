package com.github.itsanikethere.botler.interaction.handler;

import com.github.itsanikethere.botler.interaction.SongInteractionValidator;
import com.github.itsanikethere.botler.player.ServerPlayer;
import com.github.itsanikethere.botler.player.ServerPlayerFactory;

import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

public class PlayInteractionHandler {

    public static void handle(SlashCommandInteraction interaction) {
        if (SongInteractionValidator.isInvalidInteraction((Interaction) interaction)) {
            return;
        }

        interaction.respondLater();

        Server server = interaction
                .getServer()
                .orElseThrow(AssertionError::new);

        User user = interaction.getUser();

        AudioConnection botAudioConnection = getBotAudioConnection(server, user);
        play(server, user, botAudioConnection, interaction);
    }

    private static AudioConnection getBotAudioConnection(Server server, User user) {
        return server
                .getAudioConnection()
                .orElseGet(() -> {
                    ServerVoiceChannel userVoiceChannel = getUserVoiceChannel(user, server);
                    return joinUserVoiceChannel(userVoiceChannel);
                });
    }

    private static ServerVoiceChannel getUserVoiceChannel(User user, Server server) {
        return user
                .getConnectedVoiceChannel(server)
                .orElseThrow(AssertionError::new);
    }

    private static AudioConnection joinUserVoiceChannel(ServerVoiceChannel userVoiceChannel) {
        return userVoiceChannel
                .connect()
                .join();
    }

    private static void play(Server server, User user, AudioConnection botAudioConnection,
                             SlashCommandInteraction interaction) {
        ServerPlayer serverPlayer = ServerPlayerFactory.get(server.getId(), interaction.getApi());

        setSongAudioSourceIfNeeded(botAudioConnection, serverPlayer);

        String query = interaction
                .getOptionByName("query")
                .flatMap(SlashCommandInteractionOption::getStringValue)
                .orElseThrow(AssertionError::new);

        TextChannel textChannel = interaction
                .getChannel()
                .orElseThrow(AssertionError::new);

        serverPlayer.play(query, user, textChannel, interaction.createFollowupMessageBuilder());
    }

    private static void setSongAudioSourceIfNeeded(AudioConnection botAudioConnection, ServerPlayer serverPlayer) {
        if (botAudioConnection.getAudioSource().isEmpty()) {
            botAudioConnection.setAudioSource(serverPlayer.getSongAudioSource());
        }
    }

}
