package com.github.itsanikethere.botler.interaction;

import com.github.itsanikethere.botler.constant.ErrorMessageConstants;
import com.github.itsanikethere.botler.util.ErrorMessageSender;

import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.Interaction;

import java.util.Optional;

public class SongInteractionValidator {

    public static boolean isInvalidInteraction(Interaction interaction) {
        Server server = interaction
                .getServer()
                .orElseThrow(AssertionError::new);

        Optional<ServerVoiceChannel> userVoiceChannel = interaction
                .getUser()
                .getConnectedVoiceChannel(server);

        Optional<AudioConnection> botAudioConnection = server.getAudioConnection();

        if (userVoiceChannel.isEmpty()) {
            ErrorMessageSender.send(ErrorMessageConstants.NOT_CONNECTED_TO_VC, interaction.createImmediateResponder());
            return true;
        }

        if (botAudioConnection.isEmpty()) {
            return false;
        }

        if (userVoiceChannel.get().getId() != botAudioConnection.get().getChannel().getId()) {
            ErrorMessageSender.send(
                    ErrorMessageConstants.NOT_CONNECTED_TO_SAME_VC, interaction.createImmediateResponder()
            );
            return true;
        }

        return false;
    }

}
