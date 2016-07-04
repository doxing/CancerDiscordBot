package xyz.shekels.alice.cancerdiscordbot.command;

import lombok.Getter;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.shekels.alice.cancerdiscordbot.command.commands.*;

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
            new PlayCommand("play", "Plays music, `play songtitle` to play song"),
            new QueueCommand("queue", "Lists song queue"),
            new SkipCommand("skip", "Skips song"),
            new StopCommand("stop", "Kills bot"),
            new PauseCommand("pause", "Pauses / Unpauses song"),
            new ListCommand("list", "Lists artists and albums available for play"),
            new SelectCommand("select", "Used to select a song from a list when multiple are available to play")
    );

    public void parse(IMessage message) {
        if (message.getContent().startsWith("$")) {
            String[] finalMessage = message.getContent().replace("$", "").split(" ");
            System.out.println("Command: \"" + message.getContent() + "\" received from user: " + message.getAuthor().getName());
            commands.stream().filter(command -> command.getCommand().equals(finalMessage[0])).forEach(command -> {
                try {
                    command.execute(message);
                } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
