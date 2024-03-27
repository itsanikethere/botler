package com.github.itsanikethere.botler.interaction.slash;

import com.github.itsanikethere.botler.interaction.SongControlInteractionHandler;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.Collections;

public class FastForwardSlashCommand extends BaseSlashCommand {

    @Override
    public SlashCommandBuilder getBuilder() {
        return new SlashCommandBuilder()
                .setName(getName())
                .setDescription("To fast-forward the player")
                .setOptions(Collections.singletonList(
                        SlashCommandOption.createLongOption(
                                "seconds", "Fast-forward duration in seconds", false
                        )
                ))
                .setEnabledInDms(false);
    }

    @Override
    public void execute(SlashCommandInteraction interaction) {
        SongControlInteractionHandler.handle((Interaction) interaction);
    }

    @Override
    public String getName() {
        return "fast-forward";
    }

}
