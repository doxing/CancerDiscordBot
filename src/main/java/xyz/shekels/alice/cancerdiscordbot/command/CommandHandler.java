package xyz.shekels.alice.cancerdiscordbot.command;

import lombok.Getter;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.shekels.alice.cancerdiscordbot.command.commands.HelpCommand;
import xyz.shekels.alice.cancerdiscordbot.command.commands.MusicCommand;

import java.util.Arrays;
import java.util.List;

/**
 * @author alice
 * @since 7/1/16
 */
public class CommandHandler {

    @Getter
    private static List<? extends Command> commands = Arrays.asList(
            new HelpCommand("help", "Lists commands and their descriptions"),
            new MusicCommand("music", "Plays music, `music list` to list songs, can search by album, artist, and title")
    );

    public void parse(IMessage message) {
        if (message.getContent().startsWith("$")) {
            String[] finalMessage = message.getContent().replace("$", "").split(" ");

            commands.forEach(command -> {
                if (command.getCommand().equals(finalMessage[0])) {
                    try {
                        command.execute(message);
                    } catch (DiscordException | MissingPermissionsException | RateLimitException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
