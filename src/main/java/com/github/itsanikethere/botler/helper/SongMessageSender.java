package com.github.itsanikethere.botler.helper;

import com.github.itsanikethere.botler.constant.BotConstants;
import com.github.itsanikethere.botler.dto.Song;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public class SongMessageSender {

    private SongMessageSender() {
    }

    public static CompletableFuture<Message> sendNowPlayingMessage(Song song, User bot) {
        AudioTrackInfo trackInfo = song.trackInfo();

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(BotConstants.BOTLER_COLOR)
                .setThumbnail(YoutubeHelper.buildThumbnailUrl(trackInfo.uri))
                .setTitle(":cd: Now Playing")
                .setDescription(
                        String.format("> [%s](%s)\n> \u2022 **(%s)**\n> \u2022 %s",
                                trackInfo.title, trackInfo.uri, YoutubeHelper.formatDuration(trackInfo.length),
                                song.requestingUser().getMentionTag()
                        )
                )
                .setFooter("Song By: " + trackInfo.author, bot.getAvatar())
                .setTimestampToNow();

        MessageBuilder message = new MessageBuilder()
                .addEmbeds(embed)
                .addComponents(
                        ActionRow.of(
                                Button.primary("pause-resume", "PAUSE-RESUME"),
                                Button.primary("skip", "SKIP"),
                                Button.danger("stop", "STOP")
                        ),
                        ActionRow.of(
                                Button.primary("replay", "REPLAY"),
                                Button.primary("fast-forward", "FAST-FORWARD"),
                                Button.primary("rewind", "REWIND")
                        )
                );

        return message.send(song.requestChannel());
    }

    public static void sendSongQueueMessage(Song song, int position, InteractionFollowupMessageBuilder responder) {
        AudioTrackInfo trackInfo = song.trackInfo();

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(BotConstants.BOTLER_COLOR)
                .setTitle(":hourglass: Added to Queue")
                .setDescription(
                        String.format("**[%s](%s) \u30FB (%s)**",
                                trackInfo.title, trackInfo.uri, YoutubeHelper.formatDuration(trackInfo.length)
                        )
                )
                .setFooter("Queue Position: " + position);

        send(embed, responder);
    }

    public static void sendPlaylistQueueMessage(int size, String name, InteractionFollowupMessageBuilder responder) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(BotConstants.BOTLER_COLOR)
                .setTitle(":hourglass: Added to Queue")
                .setDescription(String.format("**Added `%d` tracks to the queue from `%s`**", size, name));

        send(embed, responder);
    }

    public static void sendPlayerPauseOrResumeMessage(User requestingUser, boolean isPaused,
                                                      InteractionFollowupMessageBuilder responder) {
        EmbedBuilder embed = buildSimpleEmbed(
                String.format("> %s\n> **%s %s the player**",
                        requestingUser.getMentionTag(), (isPaused ? ":arrow_forward:" : ":pause_button:"),
                        (isPaused ? "Resumed" : "Paused")
                )
        );

        send(embed, responder);
    }

    public static void sendSongSkipMessage(User requestingUser, AudioTrackInfo trackInfo,
                                           InteractionFollowupMessageBuilder responder) {
        EmbedBuilder embed = buildSimpleEmbed(
                "> " + requestingUser.getMentionTag() + "\n> **:track_next: Skipped " + trackInfo.title + "**"
        );

        send(embed, responder);
    }

    public static void sendPlayerStopMessage(User requestingUser, InteractionFollowupMessageBuilder responder) {
        EmbedBuilder embed = buildSimpleEmbed(
                "> " + requestingUser.getMentionTag() + "\n> **:stop_button: Stopped the player**"
        );

        send(embed, responder);
    }

    public static void sendSongReplayMessage(User requestingUser, InteractionFollowupMessageBuilder responder) {
        EmbedBuilder embed = buildSimpleEmbed(
                "> " + requestingUser.getMentionTag() + "\n> **:repeat: Replayed the song**"
        );

        send(embed, responder);
    }

    public static void sendPlayerFastForwardMessage(User requestingUser, long seconds,
                                                    InteractionFollowupMessageBuilder responder) {
        EmbedBuilder embed = buildSimpleEmbed(
                String.format("> %s\n> **:fast_forward: Fast-forwarded the song (%ds)**",
                        requestingUser.getMentionTag(), seconds
                )
        );

        send(embed, responder);
    }

    public static void sendPlayerRewindMessage(User requestingUser, long seconds,
                                               InteractionFollowupMessageBuilder responder) {
        EmbedBuilder embed = buildSimpleEmbed(
                String.format("> %s\n> **:rewind: Rewinded the song (%ds)**",
                        requestingUser.getMentionTag(), seconds
                )
        );

        send(embed, responder);
    }

    private static EmbedBuilder buildSimpleEmbed(String message) {
        return new EmbedBuilder()
                .setColor(BotConstants.BOTLER_COLOR)
                .setDescription(message);
    }

    private static void send(EmbedBuilder embed, InteractionFollowupMessageBuilder responder) {
        responder
                .addEmbed(embed)
                .send();
    }

}
