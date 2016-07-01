package xyz.shekels.alice.cancerdiscordbot.command.commands;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.io.FilenameUtils;
import sx.blah.discord.handle.audio.IAudioManager;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.HTTP429Exception;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.Command;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static sx.blah.discord.util.MessageBuilder.Styles.CODE;
import static xyz.shekels.alice.cancerdiscordbot.util.MessageUtil.addEffect;

/**
 * @author alice
 * @since 7/1/16
 */
public class MusicCommand extends Command {

    File musicDirectory = new File("/home/alice/Music");
    HashMap<File, List<File>> albums = new HashMap<>();

    public MusicCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) {
        String[] words = message.getContent().split(" ");

        List<File> artists = Arrays.asList(musicDirectory.listFiles());

        artists.forEach(artist -> {
            albums.put(artist, Arrays.asList(artist.listFiles()));
        });
        if (words.length == 2) {
            if (words[1].equals("list")) {
                final String[] musicList = {""};
                albums.forEach((k, v) -> {
                    final String[] organizedWorks = {k.getName() + "\n"};
                    v.forEach(album -> {
                        organizedWorks[0] += "-" + album.getName() + "\n";
                    });
                    musicList[0] += organizedWorks[0];
                });
                try {
                    message.getChannel().sendMessage(addEffect(musicList[0].replaceAll("[^a-zA-Z\\d\\s$-]", ""), CODE));
                } catch (MissingPermissionsException | DiscordException | RateLimitException e) {
                    e.printStackTrace();
                }
            } else if (words[1].equals("clear")) {
                AudioPlayer audioPlayer = new AudioPlayer(Bot.getDiscordClient().getConnectedVoiceChannels().get(0).getGuild().getAudioManager());
                audioPlayer.getPlaylist().clear();
            } else {
                words[0] = "";
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < words.length; i++) {
                    stringBuilder.append(words[i]);
                }
                String songname = stringBuilder.toString();
                System.out.println(songname);
                albums.forEach((k, v) -> {
                    v.forEach(album -> {
                        Arrays.asList(album.listFiles()).forEach(file -> {
                            if (FilenameUtils.getExtension(file.getName()).equals("mp3")) {
                                try {
                                    if (file.exists()) {
                                        Mp3File song = new Mp3File(file);
                                        if (!message.getAuthor().getConnectedVoiceChannels().isEmpty() & songname.replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(song.getId3v2Tag().getTitle().replaceAll("[^A-Za-z0-9]", ""))) {
                                            System.out.println(song.getId3v2Tag().getTitle());
                                            message.getAuthor().getConnectedVoiceChannels().get(0).join();
                                            AudioPlayer audioPlayer = new AudioPlayer(Bot.getDiscordClient().getConnectedVoiceChannels().get(0).getGuild().getAudioManager());
                                            audioPlayer.queue(file);
                                        }
                                    }
                                } catch (IOException | UnsupportedTagException | InvalidDataException | UnsupportedAudioFileException | MissingPermissionsException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    });
                });
            }
        }
    }
}
