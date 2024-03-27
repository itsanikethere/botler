package com.github.itsanikethere.botler.util;

import com.github.itsanikethere.botler.constant.BotConstants;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

public class ErrorMessageSender {

    private ErrorMessageSender() {
    }

    public static void send(String message, InteractionImmediateResponseBuilder responder) {
        responder
                .addEmbed(buildEmbed(message))
                .respond();
    }

    public static void send(String message, InteractionFollowupMessageBuilder responder) {
        responder
                .addEmbed(buildEmbed(message))
                .send();
    }

    private static EmbedBuilder buildEmbed(String message) {
        return new EmbedBuilder()
                .setColor(BotConstants.ERROR_COLOR)
                .setDescription(message);
    }

}
