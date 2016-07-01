package xyz.shekels.alice.cancerdiscordbot.command.commands;

import lombok.Setter;
import sx.blah.discord.api.IListener;
import sx.blah.discord.handle.impl.obj.Message;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import xyz.shekels.alice.cancerdiscordbot.Main;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static xyz.shekels.alice.cancerdiscordbot.util.MessageUtil.addEffect;
import static sx.blah.discord.util.MessageBuilder.Styles.*;

/**
 * @author alice
 * @since 7/1/16
 */
public class PlayCommand extends Command {

    File musicDirectory = new File(Main.musicdir);
    HashMap<File, List<File>> albums = new HashMap<>();

    public PlayCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) throws HTTP429Exception, DiscordException, MissingPermissionsException {
        String[] words = message.getContent().split(" ");

        List<File> artists = Arrays.asList(musicDirectory.listFiles());

        artists.forEach(artist -> {
            albums.put(artist, Arrays.asList(artist.listFiles()));
        });

        if (words[1].equals("list")) {
            final String[] musicList = {""};
            albums.forEach((k, v) -> {
                final String[] organizedWorks = {k.getName() + "\n"};
                v.forEach(album -> {
                    organizedWorks[0] += "-" + album.getName() + "\n";
                });
                musicList[0] += organizedWorks[0];
            });
            message.getChannel().sendMessage(addEffect(musicList[0].replaceAll("[^a-zA-Z\\d\\s$-]", ""), CODE));
        }
    }
}
