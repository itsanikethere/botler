package com.github.itsanikethere.botler.interaction.handler;

import com.github.itsanikethere.botler.player.ServerPlayer;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

public class FastForwardInteractionHandler {

    public static void handle(ServerPlayer serverPlayer, Interaction interaction) {
        if (interaction instanceof MessageComponentInteraction) {
            serverPlayer.forward(
                    interaction.getUser(), ServerPlayer.DEFAULT_SEEK_SECONDS,
                    interaction.createFollowupMessageBuilder()
            );
            return;
        }

        long seconds = interaction
                .asSlashCommandInteraction()
                .flatMap(slashCommandInteraction -> slashCommandInteraction.getOptionByName("seconds"))
                .flatMap(SlashCommandInteractionOption::getLongValue)
                .orElse(ServerPlayer.DEFAULT_SEEK_SECONDS);

        serverPlayer.forward(interaction.getUser(), seconds, interaction.createFollowupMessageBuilder());
    }

}
