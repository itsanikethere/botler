package com.github.itsanikethere.botler.interaction.handler;

import com.github.itsanikethere.botler.player.ServerPlayer;

import org.javacord.api.interaction.Interaction;

public class StopInteractionHandler {

    public static void handle(ServerPlayer serverPlayer, Interaction interaction) {
        serverPlayer.stop(interaction.getUser(), interaction.createFollowupMessageBuilder());
    }

}
