package com.github.itsanikethere.botler.interaction.slash;

import com.github.itsanikethere.botler.interaction.handler.PlayInteractionHandler;

import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.Collections;

public class PlaySlashCommand extends BaseSlashCommand {

    @Override
    public SlashCommandBuilder getBuilder() {
        return new SlashCommandBuilder()
                .setName(getName())
                .setDescription("To play a song or playlist in ur voice channel")
                .setOptions(Collections.singletonList(
                        SlashCommandOption.createStringOption(
                                "query", "A search term or url", true
                        )
                ))
                .setEnabledInDms(false);
    }

    @Override
    public void execute(SlashCommandInteraction interaction) {
        PlayInteractionHandler.handle(interaction);
    }

}
