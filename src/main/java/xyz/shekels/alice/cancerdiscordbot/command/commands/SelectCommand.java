package xyz.shekels.alice.cancerdiscordbot.command.commands;

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
    public void execute(IMessage imessage) throws RateLimitException, DiscordException, MissingPermissionsException {
        final String message = imessage.getContent();
        final String number = message.substring(message.indexOf(" "), message.length()).trim();

        if (Integer.parseInt(number) > MusicUtil.getLimboSongs().size() - 1) {
            imessage.getChannel().sendMessage(MessageUtil.addEffect("Error: ", MessageBuilder.Styles.BOLD) + "Number " + number + " larger than amount of songs in limbo.");
        } else {
            MusicUtil.playSong(imessage, MusicUtil.getLimboSongs().get(Integer.parseInt(number)));
            MusicUtil.getLimboSongs().clear();
        }
    }
}
