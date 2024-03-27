package com.github.itsanikethere.botler.listener;

import com.github.itsanikethere.botler.interaction.slash.BaseSlashCommand;
import com.github.itsanikethere.botler.producer.SlashCommandSetProducer;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.util.Set;

public class SlashCommandListener implements SlashCommandCreateListener {

    private static final Set<BaseSlashCommand> SLASH_COMMANDS = SlashCommandSetProducer.get();

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        SlashCommandInteraction interaction = event.getSlashCommandInteraction();

        String commandName = interaction.getFullCommandName();

        SLASH_COMMANDS
                .stream()
                .filter(slashCommand -> slashCommand.getName().equals(commandName))
                .findFirst()
                .ifPresent(slashCommand -> slashCommand.execute(interaction));
    }

}
