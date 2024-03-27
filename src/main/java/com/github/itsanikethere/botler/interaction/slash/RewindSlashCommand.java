package com.github.itsanikethere.botler.interaction.slash;

import com.github.itsanikethere.botler.interaction.SongControlInteractionHandler;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.Collections;

public class RewindSlashCommand extends BaseSlashCommand {

    @Override
    public SlashCommandBuilder getBuilder() {
        return new SlashCommandBuilder()
                .setName(getName())
                .setDescription("To rewind the player")
                .setOptions(Collections.singletonList(
                        SlashCommandOption.createLongOption(
                                "seconds", "Rewind duration in seconds", false
                        )
                ))
                .setEnabledInDms(false);
    }

    @Override
    public void execute(SlashCommandInteraction interaction) {
        SongControlInteractionHandler.handle((Interaction) interaction);
    }

}
