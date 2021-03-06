package xyz.shekels.alice.cancerdiscordbot.command;

import lombok.Getter;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * @author alice
 * @since 7/1/16
 */
public abstract class Command {

    @Getter
    private String command;
    @Getter
    private String description;

    public Command(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public abstract void execute(IMessage message) throws RateLimitException, DiscordException, MissingPermissionsException;
}
