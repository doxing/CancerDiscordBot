package xyz.shekels.alice.cancerdiscordbot.events.listeners;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import sx.blah.discord.util.audio.events.TrackStartEvent;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.commands.PlayCommand;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

import static sx.blah.discord.util.MessageBuilder.Styles.BOLD;
import static xyz.shekels.alice.cancerdiscordbot.util.MessageUtil.addEffect;

/**
 * @author alice
 * @since 7/4/16
 */
public class MusicListener {
    @EventSubscriber
    public void onTrackStartEvent(TrackStartEvent event) {
        try {
            PlayCommand.channel.sendMessage(addEffect("Now Playing: ", BOLD) + MusicUtil.getSongInfo(event.getTrack()));
            Bot.getDiscordClient().changeStatus(Status.game(MusicUtil.getSongInfo(event.getTrack())));
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onTrackFinishEvent(TrackFinishEvent event) {
        if (!event.getNewTrack().isPresent()) {
            Bot.getDiscordClient().getConnectedVoiceChannels().get(0).leave();
            Bot.getDiscordClient().changeStatus(Status.game("ded"));
        }
    }
}
