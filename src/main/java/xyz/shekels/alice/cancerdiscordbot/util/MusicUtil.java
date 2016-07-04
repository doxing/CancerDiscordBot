package xyz.shekels.alice.cancerdiscordbot.util;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.sun.org.apache.xpath.internal.SourceTree;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import sx.blah.discord.util.audio.events.TrackStartEvent;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.commands.PlayCommand;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static sx.blah.discord.util.MessageBuilder.Styles.BOLD;
import static xyz.shekels.alice.cancerdiscordbot.util.MessageUtil.addEffect;

/**
 * @author alice
 * @since 7/2/16
 */
public class MusicUtil {

    public static final File musicDirectory = new File("/home/alice/Music");
    private static List<File> songs = new ArrayList<>();
    @Getter
    @Setter
    private static List<File> limboSongs = new ArrayList<>();

    public static void populate() {
        try {
            Files.walk(musicDirectory.toPath()).filter(path -> Files.isRegularFile(path)).forEach(song -> songs.add(song.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean joinChannel(IMessage message) {
        try {
            if (!Bot.getDiscordClient().getConnectedVoiceChannels().contains(message.getAuthor().getConnectedVoiceChannels().get(0))) {
                message.getAuthor().getConnectedVoiceChannels().get(0).join();
                return true;
            }
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void playSong(IMessage message, File song) {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(message.getGuild());
        if (joinChannel(message)) {
            try {
                AudioPlayer.Track track = new AudioPlayer.Track(AudioSystem.getAudioInputStream(song));
                track.getMetadata().put("artist", getMp3Artist(getMp3(song)));
                track.getMetadata().put("album", getMp3Album(getMp3(song)));
                track.getMetadata().put("name", getMp3Title(getMp3(song)));
                audioPlayer.queue(track);
                System.out.println("Playing: " + getSongInfo(track));
            } catch (IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
    }

    public static void pauseSong(IMessage message) {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(message.getGuild());
        audioPlayer.setPaused(!audioPlayer.isPaused());
        System.out.println("Paused " + getSongInfo(audioPlayer.getCurrentTrack()));
    }

    public static void skipSong(IMessage message) {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(message.getGuild());
        System.out.println("Skipping: " + getSongInfo(audioPlayer.getCurrentTrack()));
        audioPlayer.skip();
    }

    public static List<AudioPlayer.Track> getQueue(IMessage message) {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(message.getGuild());
        return audioPlayer.getPlaylist();
    }

    @Nullable
    public static Mp3File getMp3(File song) {
        try {
            return new Mp3File(song);
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    public static String getMp3TitleStripped(Mp3File mp3) {
        return MessageUtil.filterText(mp3.getId3v2Tag().getTitle());
    }

    public static String getMp3Title(Mp3File mp3) {
        return mp3.getId3v2Tag().getTitle();
    }

    public static String getMp3Artist(Mp3File mp3) {
        return mp3.getId3v2Tag().getArtist();
    }

    public static String getMp3Album(Mp3File mp3) {
        return mp3.getId3v2Tag().getAlbum();
    }

    public static String getSongInfo(Mp3File mp3) {
        return getMp3Artist(mp3) + ", " + getMp3Album(mp3) + ", " + getMp3Title(mp3);
    }

    public static void kill(IMessage message) {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(message.getGuild());
        audioPlayer.getPlaylist().clear();
        audioPlayer.clean();
        if (!Bot.getDiscordClient().getConnectedVoiceChannels().isEmpty()) {
            Bot.getDiscordClient().getConnectedVoiceChannels().get(0).leave();
        }
    }

    public static String getSongInfo(AudioPlayer.Track track) {
        return track.getMetadata().get("artist") + ", " + track.getMetadata().get("album") + ", " + track.getMetadata().get("name");
    }

    @NotNull
    static Predicate<File> isValidMp3(String title) {
        return song -> {
            if (!song.exists() || !FilenameUtils.getExtension(song.getName()).equals("mp3")) {
                return false;
            }
            Mp3File mp3 = getMp3(song);
            if (mp3 == null || !getMp3TitleStripped(mp3).contains(MessageUtil.filterText(title))) {
                return false;
            }
            return true;
        };
    }

    public static List<File> fromFile(String title) {
        return songs.stream().filter(isValidMp3(title)).collect(Collectors.toList());
    }
}
