package xyz.shekels.alice.cancerdiscordbot.command;

import lombok.Getter;
import sx.blah.discord.api.IListener;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;
import xyz.shekels.alice.cancerdiscordbot.command.commands.HelpCommand;
import xyz.shekels.alice.cancerdiscordbot.command.commands.PlayCommand;
import xyz.shekels.alice.cancerdiscordbot.command.commands.QueueCommand;
import xyz.shekels.alice.cancerdiscordbot.events.listeners.MessageRecievedListener;

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
            new PlayCommand("play", "Plays music, `$play list` to list songs, can search by album, artist, and title"),
            new QueueCommand("queue", "`$queue` lists songs in queue, `$queue clear` clears")
    );

    public void parse(IMessage message) {
        if (message.getContent().startsWith("$")) {
            String[] finalMessage = message.getContent().replace("$", "").split(" ");

            commands.forEach(command -> {
                if (command.getCommand().equals(finalMessage[0])) {
                    try {
                        command.execute(message);
                    } catch (HTTP429Exception | DiscordException | MissingPermissionsException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
