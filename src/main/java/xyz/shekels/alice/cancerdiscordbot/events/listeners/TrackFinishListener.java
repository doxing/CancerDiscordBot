package xyz.shekels.alice.cancerdiscordbot.events.listeners;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.commands.PlayCommand;
import xyz.shekels.alice.cancerdiscordbot.util.MusicUtil;

import static sx.blah.discord.util.MessageBuilder.Styles.BOLD;
import static xyz.shekels.alice.cancerdiscordbot.util.MessageUtil.addEffect;

/**
 * @author alice
 * @since 7/1/16
 */
public class TrackFinishListener implements IListener<TrackFinishEvent> {

    @Override
    public void handle(TrackFinishEvent audioStopEvent) {
        if (!audioStopEvent.getNewTrack().isPresent()) {
            Bot.getDiscordClient().getConnectedVoiceChannels().get(0).leave();
            Bot.getDiscordClient().changeStatus(Status.game("ded"));
        }
    }
}
