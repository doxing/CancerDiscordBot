package xyz.shekels.alice.cancerdiscordbot.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * @author alice
 * @since 7/1/16
 */
public class MessageUtil {

    @Contract(pure = true)
    public static String addEffect(String string, MessageBuilder.Styles style) {
        return style + string + style;
    }

    @NotNull
    public static String filterText(String text) {
        return text.replaceAll("[^A-Za-z0-9$]", "").toLowerCase();
    }

    public static void replyToMessage(IMessage replyTo, String response) {
        try {
            replyTo.getChannel().sendMessage(response);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

}
