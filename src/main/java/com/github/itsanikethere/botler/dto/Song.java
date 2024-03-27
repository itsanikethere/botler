package com.github.itsanikethere.botler.dto;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

public record Song(User requestingUser, TextChannel requestChannel, AudioTrack track, AudioTrackInfo trackInfo) {
}
