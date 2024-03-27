package com.github.itsanikethere.botler.listener;

import com.github.itsanikethere.botler.interaction.button.BaseButton;
import com.github.itsanikethere.botler.producer.ButtonSetProducer;

import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;

import java.util.Set;

public class ButtonListener implements MessageComponentCreateListener {

    private static final Set<BaseButton> BUTTONS = ButtonSetProducer.get();

    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        MessageComponentInteraction interaction = event.getMessageComponentInteraction();

        String customId = interaction.getCustomId();

        BUTTONS
                .stream()
                .filter(button -> button.getName().equals(customId))
                .findFirst()
                .ifPresent(button -> button.execute(interaction));
    }

}
