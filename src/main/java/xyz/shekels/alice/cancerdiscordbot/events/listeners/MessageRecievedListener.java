package xyz.shekels.alice.cancerdiscordbot.events.listeners;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import xyz.shekels.alice.cancerdiscordbot.command.CommandHandler;

/**
 * @author alice
 * @since 7/1/16
 */
public class MessageRecievedListener implements IListener<MessageReceivedEvent> {

    /**
     * This is the class that handles all sent commands and parses information sent to the bot in the form of a message.
     */

    CommandHandler handler = new CommandHandler();

    /**
     * @param messageReceivedEvent
     */
    public void handle(MessageReceivedEvent messageReceivedEvent) {
        IMessage message = messageReceivedEvent.getMessage();
        handler.parse(message);
    }
}
