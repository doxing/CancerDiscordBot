package xyz.shekels.alice.cancerdiscordbot.command.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
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
    public void execute(IMessage message) throws HTTP429Exception, DiscordException, MissingPermissionsException {
        CommandHandler.getCommands().forEach(command -> {
            try {
                message.getChannel().sendMessage("$" + addEffect(command.getCommand(), BOLD) + ": " + command.getDescription());
            } catch (MissingPermissionsException | HTTP429Exception | DiscordException e) {
                e.printStackTrace();
            }
        });
    }
}
