package xyz.shekels.alice.cancerdiscordbot;

import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.commands.PlayCommand;
import xyz.shekels.alice.cancerdiscordbot.events.ListenerHandler;

/**
 * @author alice
 * @since 7/1/16
 */
public class Main {

    public static String musicdir = "";

    public static void main(String[] args) {
        Bot.setTOKEN(args[0]);
        musicdir = args[1];
        ListenerHandler.registerListeners();
    }
}
