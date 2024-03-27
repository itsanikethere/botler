package com.github.itsanikethere.botler.interaction.handler;

import com.github.itsanikethere.botler.player.ServerPlayer;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

public class RewindInteractionHandler {

    public static void handle(ServerPlayer serverPlayer, Interaction interaction) {
        if (interaction instanceof MessageComponentInteraction) {
            serverPlayer.rewind(
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

        serverPlayer.rewind(interaction.getUser(), seconds, interaction.createFollowupMessageBuilder());
    }

}
