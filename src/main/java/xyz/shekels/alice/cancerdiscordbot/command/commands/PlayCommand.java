package xyz.shekels.alice.cancerdiscordbot.command.commands;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.sun.org.apache.xpath.internal.SourceTree;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.Command;
import xyz.shekels.alice.cancerdiscordbot.util.MessageUtil;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static sx.blah.discord.util.MessageBuilder.Styles.CODE;

/**
 * @author alice
 * @since 7/1/16
 */
public class PlayCommand extends Command {

    public static IChannel channel = null;

    public PlayCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) throws MissingPermissionsException {
        String[] words = message.getContent().split(" ");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            stringBuilder.append(words[i]);
        }

        String songName = stringBuilder.toString().replace("$play", "");

        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(message.getGuild());

        if (words.length > 1) {
            List<File> songs = MusicUtil.fromFile(songName);
            if (!songs.isEmpty()) {
                if (!message.getAuthor().getConnectedVoiceChannels().isEmpty() && Bot.getDiscordClient().getConnectedVoiceChannels().isEmpty()) {
                    if (songs.size() == 1) {
                        MusicUtil.playSong(message, songs.get(0));
                    } else {
                        if (songs.size() > 1) {
                            String[] buffer = {"Multiple matches found! Enter the number of the track you'd like to play.\n"};
                            songs.stream().filter(File::exists).forEach(song -> buffer[0] += "**" + songs.indexOf(song) + ".** " + MusicUtil.getSongInfo(MusicUtil.getMp3(song)) + "\n");
                            MusicUtil.setLimboSongs(songs);
                            try {
                                message.getChannel().sendMessage(buffer[0]);
                            } catch (RateLimitException | DiscordException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
