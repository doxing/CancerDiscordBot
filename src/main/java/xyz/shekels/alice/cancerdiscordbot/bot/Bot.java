package xyz.shekels.alice.cancerdiscordbot.bot;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.io.*;
import java.nio.file.Files;

/**
 * @author alice
 * @since 7/1/16
 */
public class Bot {

    /**
     * Bot class, contains the token in variable TOKEN and contains method to login.
     */
    @Getter
    public static IDiscordClient discordClient = getClient();

    @Nullable
    private static IDiscordClient getClient(){
        String[] token = {""};
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Bot.class.getResourceAsStream("/token")));
            br.lines().forEach(line -> token[0] += line);
            System.out.println("Attempting to log in");
            return new ClientBuilder().withToken(token[0]).login();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        return null;
    }

}
