package xyz.shekels.alice.cancerdiscordbot.events;

import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.events.listeners.MessageRecievedListener;
import xyz.shekels.alice.cancerdiscordbot.events.listeners.ReadyListener;
import xyz.shekels.alice.cancerdiscordbot.events.listeners.TrackFinishListener;
import xyz.shekels.alice.cancerdiscordbot.events.listeners.TrackStartListener;

import java.util.Arrays;
import java.util.List;

/**
 * @author alice
 * @since 7/1/16
 */
public class ListenerHandler {

    /**
     * Class that handles Listener functions
     */

    private static EventDispatcher eventDispatcher = Bot.getDiscordClient().getDispatcher();
    private static List<? extends IListener> listeners = Arrays.asList(new MessageRecievedListener(), new ReadyListener(), new TrackStartListener(), new TrackFinishListener());

    public static void registerListeners() {
        listeners.forEach(eventDispatcher::registerListener);
    }
}
