package com.github.itsanikethere.botler.interaction;

import com.github.itsanikethere.botler.interaction.handler.FastForwardInteractionHandler;
import com.github.itsanikethere.botler.interaction.handler.PauseResumeInteractionHandler;
import com.github.itsanikethere.botler.interaction.handler.ReplayInteractionHandler;
import com.github.itsanikethere.botler.interaction.handler.RewindInteractionHandler;
import com.github.itsanikethere.botler.interaction.handler.SkipInteractionHandler;
import com.github.itsanikethere.botler.interaction.handler.StopInteractionHandler;
import com.github.itsanikethere.botler.player.ServerPlayer;
import com.github.itsanikethere.botler.player.ServerPlayerFactory;

import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SongControlInteractionHandler {

    public static void handle(Interaction interaction) {
        if (SongInteractionValidator.isInvalidInteraction(interaction)) {
            return;
        }

        interaction.respondLater();

        Server server = interaction
                .getServer()
                .orElseThrow(AssertionError::new);

        ServerPlayer serverPlayer = ServerPlayerFactory.get(server.getId(), interaction.getApi());

        if (!serverPlayer.acquireSongControl(interaction)) {
            return;
        }

        switch (getUniqueId(interaction)) {
            case "pause-resume" -> PauseResumeInteractionHandler.handle(serverPlayer, interaction);
            case "skip" -> SkipInteractionHandler.handle(serverPlayer, interaction);
            case "stop" -> StopInteractionHandler.handle(serverPlayer, interaction);
            case "replay" -> ReplayInteractionHandler.handle(serverPlayer, interaction);
            case "fast-forward" -> FastForwardInteractionHandler.handle(serverPlayer, interaction);
            case "rewind" -> RewindInteractionHandler.handle(serverPlayer, interaction);
        }
    }

    private static String getUniqueId(Interaction interaction) {
        return interaction instanceof MessageComponentInteraction ?
                ((MessageComponentInteraction) interaction).getCustomId() :
                ((SlashCommandInteraction) interaction).getFullCommandName();
    }

}
