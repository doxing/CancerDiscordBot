package xyz.shekels.alice.cancerdiscordbot.command.commands;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;
import xyz.shekels.alice.cancerdiscordbot.command.Command;

/**
 * @author alice
 * @since 7/1/16
 */
public class QueueCommand extends Command {

    public QueueCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) throws HTTP429Exception, DiscordException, MissingPermissionsException {

    }
}
