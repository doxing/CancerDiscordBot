package xyz.shekels.alice.cancerdiscordbot.events.listeners;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.events.TrackStartEvent;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.commands.PlayCommand;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

import static sx.blah.discord.util.MessageBuilder.Styles.BOLD;
import static xyz.shekels.alice.cancerdiscordbot.util.MessageUtil.addEffect;

/**
 * @author alice
 * @since 7/3/16
 */
public class TrackStartListener implements IListener<TrackStartEvent> {

    @Override
    public void handle(TrackStartEvent trackStartEvent) {
        try {
            PlayCommand.channel.sendMessage(addEffect("Now Playing: ", BOLD) + MusicUtil.getSongInfo(trackStartEvent.getTrack()));
            Bot.getDiscordClient().changeStatus(Status.game(MusicUtil.getSongInfo(trackStartEvent.getTrack())));
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }
}
