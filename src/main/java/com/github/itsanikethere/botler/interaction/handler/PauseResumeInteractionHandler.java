package com.github.itsanikethere.botler.interaction.handler;

import com.github.itsanikethere.botler.player.ServerPlayer;

import org.javacord.api.interaction.Interaction;

public class PauseResumeInteractionHandler {

    public static void handle(ServerPlayer serverPlayer, Interaction interaction) {
        serverPlayer.pauseOrResume(interaction.getUser(), interaction.createFollowupMessageBuilder());
    }

}
