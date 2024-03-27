package com.github.itsanikethere.botler.interaction.handler;

import com.github.itsanikethere.botler.constant.BotConstants;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.Interaction;
import org.javacord.api.util.logging.ExceptionLogger;

public class AboutInteractionHandler {

    public static void handle(Interaction interaction) {
        DiscordApi api = interaction.getApi();

        api
                .getUserById(BotConstants.DEVELOPER_ID)
                .thenAccept(developer -> {
                    User bot = api.getYourself();

                    EmbedBuilder embed = new EmbedBuilder()
                            .setAuthor(developer)
                            .setColor(BotConstants.BOTLER_COLOR)
                            .setThumbnail(bot.getAvatar())
                            .setTitle(bot.getName() + " - Your personal DJ")
                            .setDescription("Plays songs from YouTube with control features.")
                            .addInlineField("GitHub", BotConstants.GITHUB_URL)
                            .addInlineField("Support Server", BotConstants.SUPPORT_SERVER_URL);

                    interaction
                            .createImmediateResponder()
                            .addEmbed(embed)
                            .respond();
                })
                .exceptionally(ExceptionLogger.get());
    }

}
