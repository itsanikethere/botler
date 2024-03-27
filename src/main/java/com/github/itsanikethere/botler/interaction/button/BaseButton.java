package com.github.itsanikethere.botler.interaction.button;

import org.javacord.api.interaction.MessageComponentInteraction;

public abstract class BaseButton {

    private static final String BUTTON_SUFFIX = "Button";
    private static final int BUTTON_SUFFIX_LENGTH = BUTTON_SUFFIX.length();

    public abstract void execute(MessageComponentInteraction interaction);

    public String getName() {
        String className = getClass().getSimpleName();

        if (!className.endsWith(BUTTON_SUFFIX)) {
            throw new IllegalStateException("Class name must end with " + BUTTON_SUFFIX + ".");
        }

        return className
                .substring(0, className.length() - BUTTON_SUFFIX_LENGTH)
                .toLowerCase();
    }

}
