package xyz.shekels.alice.cancerdiscordbot;

import xyz.shekels.alice.cancerdiscordbot.events.ListenerHandler;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

/**
 * @author alice
 * @since 7/1/16
 */
public class Main {

    public static void main(String[] args) {
        ListenerHandler.registerListeners();
        MusicUtil.populate();
    }
}
