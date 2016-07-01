package xyz.shekels.alice.cancerdiscordbot.events.listeners;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;
import xyz.shekels.alice.cancerdiscordbot.command.commands.MusicCommand;

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
        } else if (audioStopEvent.getNewTrack().isPresent()) {
            try {
                MusicCommand.channel.sendMessage(addEffect("Now Playing: ", BOLD) + audioStopEvent.getNewTrack().get().getMetadata().get("artist") + ", " + audioStopEvent.getNewTrack().get().getMetadata().get("album") + ", " + audioStopEvent.getNewTrack().get().getMetadata().get("name"));
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
        }
    }
}
