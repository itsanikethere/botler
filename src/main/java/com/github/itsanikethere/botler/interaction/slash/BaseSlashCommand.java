package com.github.itsanikethere.botler.interaction.slash;

import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public abstract class BaseSlashCommand {

    private static final String SLASH_COMMAND_SUFFIX = "SlashCommand";
    private static final int SLASH_COMMAND_SUFFIX_LENGTH = SLASH_COMMAND_SUFFIX.length();

    public abstract SlashCommandBuilder getBuilder();

    public abstract void execute(SlashCommandInteraction interaction);

    public String getName() {
        String className = getClass().getSimpleName();

        if (!className.endsWith(SLASH_COMMAND_SUFFIX)) {
            throw new IllegalStateException("Class name must end with " + SLASH_COMMAND_SUFFIX + ".");
        }

        return className
                .substring(0, className.length() - SLASH_COMMAND_SUFFIX_LENGTH)
                .toLowerCase();
    }

}
