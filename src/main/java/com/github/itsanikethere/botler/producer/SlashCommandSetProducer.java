package com.github.itsanikethere.botler.producer;

import com.github.itsanikethere.botler.interaction.slash.AboutSlashCommand;
import com.github.itsanikethere.botler.interaction.slash.BaseSlashCommand;
import com.github.itsanikethere.botler.interaction.slash.FastForwardSlashCommand;
import com.github.itsanikethere.botler.interaction.slash.PauseResumeSlashCommand;
import com.github.itsanikethere.botler.interaction.slash.PlaySlashCommand;
import com.github.itsanikethere.botler.interaction.slash.ReplaySlashCommand;
import com.github.itsanikethere.botler.interaction.slash.RewindSlashCommand;
import com.github.itsanikethere.botler.interaction.slash.SkipSlashCommand;
import com.github.itsanikethere.botler.interaction.slash.StopSlashCommand;

import java.util.HashSet;
import java.util.Set;

public class SlashCommandSetProducer {

    private static final Set<BaseSlashCommand> SLASH_COMMANDS = new HashSet<>();

    static {
        SLASH_COMMANDS.add(new AboutSlashCommand());
        SLASH_COMMANDS.add(new FastForwardSlashCommand());
        SLASH_COMMANDS.add(new PauseResumeSlashCommand());
        SLASH_COMMANDS.add(new PlaySlashCommand());
        SLASH_COMMANDS.add(new ReplaySlashCommand());
        SLASH_COMMANDS.add(new RewindSlashCommand());
        SLASH_COMMANDS.add(new SkipSlashCommand());
        SLASH_COMMANDS.add(new StopSlashCommand());
    }

    private SlashCommandSetProducer() {
    }

    public static Set<BaseSlashCommand> get() {
        return SLASH_COMMANDS;
    }

}
