package xyz.shekels.alice.cancerdiscordbot.command.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.Command;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

import java.io.File;
import java.util.List;

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
    public void execute(IMessage imessage) throws MissingPermissionsException {
        final String message = imessage.getContent();
        final String songName = message.substring(message.indexOf(" "), message.length());
        if (message.contains(" ") && songName.length() > 0) {
            List<File> songs = MusicUtil.fromFile(songName);
            if (!songs.isEmpty()) {
                if (!imessage.getAuthor().getConnectedVoiceChannels().isEmpty()) {
                    if (songs.size() == 1) {
                        MusicUtil.playSong(imessage, songs.get(0));
                    } else {
                        if (songs.size() > 1) {
                            String[] buffer = {"Multiple matches found! Enter the number of the track you'd like to play.\n"};
                            songs.stream().filter(File::exists).forEach(song -> buffer[0] += "**" + songs.indexOf(song) + ".** " + MusicUtil.getSongInfo(MusicUtil.getMp3(song)) + "\n");
                            songs.forEach(song -> {
                                System.out.println(MusicUtil.getSongInfo(MusicUtil.getMp3(song)));
                            });
                            MusicUtil.setLimboSongs(songs);
                            try {
                                imessage.getChannel().sendMessage(buffer[0]);
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
