package com.github.itsanikethere.botler.producer;

import com.github.itsanikethere.botler.interaction.button.BaseButton;
import com.github.itsanikethere.botler.interaction.button.FastForwardButton;
import com.github.itsanikethere.botler.interaction.button.PauseResumeButton;
import com.github.itsanikethere.botler.interaction.button.ReplayButton;
import com.github.itsanikethere.botler.interaction.button.RewindButton;
import com.github.itsanikethere.botler.interaction.button.SkipButton;
import com.github.itsanikethere.botler.interaction.button.StopButton;

import java.util.HashSet;
import java.util.Set;

public class ButtonSetProducer {

    private static final Set<BaseButton> BUTTONS = new HashSet<>();

    static {
        BUTTONS.add(new FastForwardButton());
        BUTTONS.add(new PauseResumeButton());
        BUTTONS.add(new ReplayButton());
        BUTTONS.add(new RewindButton());
        BUTTONS.add(new SkipButton());
        BUTTONS.add(new StopButton());
    }

    private ButtonSetProducer() {
    }

    public static Set<BaseButton> get() {
        return BUTTONS;
    }

}
