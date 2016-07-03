package xyz.shekels.alice.cancerdiscordbot.command.commands;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.shekels.alice.cancerdiscordbot.command.Command;
import xyz.shekels.alice.cancerdiscordbot.events.ListenerHandler;
import xyz.shekels.alice.cancerdiscordbot.util.MessageUtil;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

import java.io.File;
import java.util.Arrays;

import static sx.blah.discord.util.MessageBuilder.Styles.CODE;

/**
 * @author alice
 * @since 7/3/16
 */
public class ListCommand extends Command {

    public ListCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) throws RateLimitException, DiscordException, MissingPermissionsException {
        final String[] musicList = {"Available Music:\n"};
        Arrays.stream(MusicUtil.musicDirectory.listFiles(File::isDirectory))
                .forEach(artist -> {
                    musicList[0] += artist.getName() + "\n";
                    Arrays.stream(artist.listFiles(File::isDirectory))
                            .forEach(album -> musicList[0] += "- " + album.getName() + "\n");
                });
        MessageUtil.replyToMessage(message, MessageUtil.addEffect(musicList[0], CODE));
    }
}
