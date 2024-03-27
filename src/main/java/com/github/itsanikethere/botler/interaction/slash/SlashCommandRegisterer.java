package com.github.itsanikethere.botler.interaction.slash;

import com.github.itsanikethere.botler.producer.SlashCommandSetProducer;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.Set;
import java.util.stream.Collectors;

public class SlashCommandRegisterer {

    public static void register(DiscordApi api) {
        Set<SlashCommandBuilder> slashCommandBuilders = SlashCommandSetProducer
                .get()
                .stream()
                .map(BaseSlashCommand::getBuilder)
                .collect(Collectors.toSet());

        api
                .bulkOverwriteGlobalApplicationCommands(slashCommandBuilders)
                .exceptionally(ExceptionLogger.get());
    }

}
