package xyz.shekels.alice.cancerdiscordbot.command.commands;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.shekels.alice.cancerdiscordbot.command.Command;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

/**
 * @author alice
 * @since 7/2/16
 */
public class SkipCommand extends Command {

    public SkipCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) throws RateLimitException, DiscordException, MissingPermissionsException {
        MusicUtil.skipSong(message);
    }
}
