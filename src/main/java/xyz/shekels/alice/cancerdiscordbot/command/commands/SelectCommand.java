package xyz.shekels.alice.cancerdiscordbot.command.commands;

import sun.java2d.pipe.SpanIterator;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.shekels.alice.cancerdiscordbot.command.Command;
import xyz.shekels.alice.cancerdiscordbot.util.MessageUtil;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

/**
 * @author alice
 * @since 7/3/16
 */
public class SelectCommand extends Command {

    public SelectCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) throws RateLimitException, DiscordException, MissingPermissionsException {
        String[] words = message.getContent().split(" ");

        System.out.println("???");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            stringBuilder.append(words[i]);
        }

        String number = stringBuilder.toString().replace("$select", "");

        System.out.println(number);
        if (Integer.parseInt(number) > MusicUtil.getLimboSongs().size() - 1) {
            message.getChannel().sendMessage(MessageUtil.addEffect("Error: ", MessageBuilder.Styles.BOLD) + "Number " + number + "larger than amount of songs in limbo.");
        } else {
            MusicUtil.playSong(message, MusicUtil.getLimboSongs().get(Integer.parseInt(number)));
            MusicUtil.getLimboSongs().clear();
        }
    }
}
