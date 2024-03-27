package com.github.itsanikethere.botler;

import com.github.itsanikethere.botler.interaction.slash.SlashCommandRegisterer;
import com.github.itsanikethere.botler.listener.ButtonListener;
import com.github.itsanikethere.botler.listener.SlashCommandListener;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.util.logging.ExceptionLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            LOGGER.error("Bot token not provided.");
            return;
        }

        init(args[0]);
    }

    private static void init(String token) {
        new DiscordApiBuilder()
                .addIntents(Intent.MESSAGE_CONTENT)
                .setToken(token)
                .setRecommendedTotalShards()
                .join()
                .loginAllShards()
                .forEach(shardFuture -> shardFuture
                        .thenAccept(Main::onShardLogin)
                        .exceptionally(ExceptionLogger.get())
                );
    }

    private static void onShardLogin(DiscordApi api) {
        configureApi(api);
        addListeners(api);
        registerSlashCommands(api);
        LOGGER.info("Shard #{} initialized.", api.getCurrentShard());
    }

    private static void configureApi(DiscordApi api) {
        api.setMessageCacheSize(0, 0);
        api.updateActivity(ActivityType.LISTENING, "/play");
    }

    private static void addListeners(DiscordApi api) {
        api.addSlashCommandCreateListener(new SlashCommandListener());
        api.addMessageComponentCreateListener(new ButtonListener());
    }

    private static void registerSlashCommands(DiscordApi api) {
        SlashCommandRegisterer.register(api);
    }

}
