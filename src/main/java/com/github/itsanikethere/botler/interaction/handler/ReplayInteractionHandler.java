package com.github.itsanikethere.botler.interaction.handler;

import com.github.itsanikethere.botler.player.ServerPlayer;

import org.javacord.api.interaction.Interaction;

public class ReplayInteractionHandler {

    public static void handle(ServerPlayer serverPlayer, Interaction interaction) {
        serverPlayer.replay(interaction.getUser(), interaction.createFollowupMessageBuilder());
    }

}
