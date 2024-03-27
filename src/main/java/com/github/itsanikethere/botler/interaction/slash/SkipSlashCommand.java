package com.github.itsanikethere.botler.interaction.slash;

import com.github.itsanikethere.botler.interaction.SongControlInteractionHandler;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SkipSlashCommand extends BaseSlashCommand {

    @Override
    public SlashCommandBuilder getBuilder() {
        return new SlashCommandBuilder()
                .setName(getName())
                .setDescription("To skip the current song")
                .setEnabledInDms(false);
    }

    @Override
    public void execute(SlashCommandInteraction interaction) {
        SongControlInteractionHandler.handle((Interaction) interaction);
    }

}
