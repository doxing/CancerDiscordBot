package xyz.shekels.alice.cancerdiscordbot.util;

import sx.blah.discord.util.MessageBuilder;

/**
 * @author alice
 * @since 7/1/16
 */
public class MessageUtil {

    public static String addEffect(String string, MessageBuilder.Styles style) {
        return style + string + style;
    }
}
