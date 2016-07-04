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

    public static void replyToMessage(IMessage replyTo, String... response) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < response.length; i++) {
                if (i == 0) {
                    sb.append("**" + response[0] + "**");
                } else {
                    sb.append(response[i]);
                }
            }
            replyTo.getChannel().sendMessage(sb.toString());
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    public static void replyToMessageCode(IMessage replyTo, String response) {
        try {
            replyTo.getChannel().sendMessage("```\n" + response + "\n```");
        } catch (MissingPermissionsException | DiscordException | RateLimitException e1) {
            e1.printStackTrace();
        }
    }
}