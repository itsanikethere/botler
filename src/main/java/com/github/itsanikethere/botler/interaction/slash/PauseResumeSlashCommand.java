package com.github.itsanikethere.botler.interaction.slash;

import com.github.itsanikethere.botler.interaction.SongControlInteractionHandler;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public class PauseResumeSlashCommand extends BaseSlashCommand {

    @Override
    public SlashCommandBuilder getBuilder() {
        return new SlashCommandBuilder()
                .setName(getName())
                .setDescription("To pause or resume the player")
                .setEnabledInDms(false);
    }

    @Override
    public void execute(SlashCommandInteraction interaction) {
        SongControlInteractionHandler.handle((Interaction) interaction);
    }

    @Override
    public String getName() {
        return "pause-resume";
    }

}
