package com.github.itsanikethere.botler.interaction.slash;

import com.github.itsanikethere.botler.interaction.handler.AboutInteractionHandler;

import org.javacord.api.interaction.Interaction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public class AboutSlashCommand extends BaseSlashCommand {

    @Override
    public SlashCommandBuilder getBuilder() {
        return new SlashCommandBuilder()
                .setName(getName())
                .setDescription("To see bot info")
                .setEnabledInDms(true);
    }

    @Override
    public void execute(SlashCommandInteraction interaction) {
        AboutInteractionHandler.handle((Interaction) interaction);
    }

}
