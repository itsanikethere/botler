package com.github.itsanikethere.botler.interaction.handler;

import com.github.itsanikethere.botler.player.ServerPlayer;

import org.javacord.api.interaction.Interaction;

public class SkipInteractionHandler {

    public static void handle(ServerPlayer serverPlayer, Interaction interaction) {
        serverPlayer.skip(interaction.getUser(), interaction.createFollowupMessageBuilder());
    }

}
