package xyz.shekels.alice.cancerdiscordbot.command.commands;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.*;
import xyz.shekels.alice.cancerdiscordbot.command.Command;
import xyz.shekels.alice.cancerdiscordbot.command.CommandHandler;

import static xyz.shekels.alice.cancerdiscordbot.util.MessageUtil.addEffect;
import static sx.blah.discord.util.MessageBuilder.Styles.*;

/**
 * @author alice
 * @since 7/1/16
 */
public class HelpCommand extends Command {

    public HelpCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) throws DiscordException, MissingPermissionsException, RateLimitException {
        String[] buffer = {""};
        CommandHandler.getCommands().forEach(command -> {
                buffer[0] += "**$" + command.getCommand() + "**" + ": " + command.getDescription() + "\n";
        });
        message.getChannel().sendMessage(buffer[0]);
    }
}
