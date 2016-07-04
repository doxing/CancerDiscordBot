package xyz.shekels.alice.cancerdiscordbot.events.listeners;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * @author alice
 * @since 7/4/16
 */
public class ReadyListener implements IListener<ReadyEvent> {

    @Override
    public void handle(ReadyEvent readyEvent) {
        System.out.println("Bot ready");
    }
}
