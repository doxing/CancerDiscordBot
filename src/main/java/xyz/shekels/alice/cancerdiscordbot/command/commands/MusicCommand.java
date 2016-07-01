package xyz.shekels.alice.cancerdiscordbot.command.commands;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.commons.io.FilenameUtils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.Command;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static sx.blah.discord.util.MessageBuilder.Styles.BOLD;
import static sx.blah.discord.util.MessageBuilder.Styles.CODE;
import static xyz.shekels.alice.cancerdiscordbot.util.MessageUtil.addEffect;

/**
 * @author alice
 * @since 7/1/16
 */
public class MusicCommand extends Command {

    File musicDirectory = new File("/home/alice/Music");
    HashMap<File, List<File>> albums = new HashMap<>();

    public static IChannel channel = null;

    public MusicCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(IMessage message) {
        String[] words = message.getContent().split(" ");

        channel = message.getChannel();

        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(message.getGuild());

        List<File> artists = Arrays.asList(musicDirectory.listFiles());

        artists.forEach(artist -> {
            albums.put(artist, Arrays.asList(artist.listFiles()));
        });
        if (words.length > 1) {
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
                audioPlayer.getPlaylist().clear();
                Bot.getDiscordClient().getConnectedVoiceChannels().get(0).leave();
            } else if (words[1].equals("queue")) {
                final String[] songs = {};
                audioPlayer.getPlaylist().forEach(song -> {
                    songs[0] += song.getMetadata().get("artist") + ", " + song.getMetadata().get("album") + ", " + song.getMetadata().get("name");
                });
                System.out.println(songs[0]);
                try {
                    message.getChannel().sendMessage(songs[0]);
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }

            } else {
                if (words[1].equals("skip")) {
                    audioPlayer.skip();
                } else {
                    words[0] = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < words.length; i++) {
                        stringBuilder.append(words[i]);
                    }
                    String songname = stringBuilder.toString().replaceAll("[^A-Za-z0-9]", "").replace("'", "");
                    System.out.println(songname);
                    albums.forEach((k, v) -> {
                        v.forEach(album -> {
                            Arrays.asList(album.listFiles()).forEach(file -> {
                                if (FilenameUtils.getExtension(file.getName()).equals("mp3")) {
                                    try {
                                        if (file.exists()) {
                                            Mp3File song = new Mp3File(file);

                                            if (!message.getAuthor().getConnectedVoiceChannels().isEmpty() & songname.equalsIgnoreCase(song.getId3v2Tag().getTitle().replaceAll("[^A-Za-z0-9]", ""))) {
                                                message.getAuthor().getConnectedVoiceChannels().get(0).join();
                                                AudioPlayer.Track track = new AudioPlayer.Track(AudioSystem.getAudioInputStream(file));
                                                track.getMetadata().put("name", song.getId3v2Tag().getTitle());
                                                track.getMetadata().put("album", song.getId3v2Tag().getAlbum());
                                                track.getMetadata().put("artist", song.getId3v2Tag().getAlbumArtist());
                                                audioPlayer.queue(track);
                                                message.getChannel().sendMessage(addEffect("Adding to queue: ", BOLD) + song.getId3v2Tag().getAlbumArtist() + ", " + song.getId3v2Tag().getAlbum() + ", " + song.getId3v2Tag().getTitle());
                                            }
                                        }
                                    } catch (IOException | UnsupportedTagException | InvalidDataException | UnsupportedAudioFileException | MissingPermissionsException | DiscordException | RateLimitException e) {
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
}
