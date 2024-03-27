package com.github.itsanikethere.botler.interaction.button;

import com.github.itsanikethere.botler.interaction.SongControlInteractionHandler;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.MessageComponentInteraction;

public class RewindButton extends BaseButton {

    @Override
    public void execute(MessageComponentInteraction interaction) {
        SongControlInteractionHandler.handle((Interaction) interaction);
    }

}
