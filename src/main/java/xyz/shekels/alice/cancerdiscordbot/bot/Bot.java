package xyz.shekels.alice.cancerdiscordbot.bot;

import lombok.Getter;
import lombok.Setter;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

/**
 * @author alice
 * @since 7/1/16
 */
public class Bot {

    /**
     * Bot class, contains the token in variable TOKEN and contains method to login.
     */

    @Setter
    private static String TOKEN = "";

    @Getter
    private static IDiscordClient discordClient = getClient();

    private static IDiscordClient getClient(){
        try {
            return new ClientBuilder().withToken(TOKEN).login();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        return null;
    }

}
