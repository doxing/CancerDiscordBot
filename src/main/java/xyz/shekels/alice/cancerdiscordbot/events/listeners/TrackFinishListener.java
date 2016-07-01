package xyz.shekels.alice.cancerdiscordbot.events.listeners;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.AudioStopEvent;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;

/**
 * @author alice
 * @since 7/1/16
 */
public class TrackFinishListener implements IListener<TrackFinishEvent> {

    @Override
    public void handle(TrackFinishEvent audioStopEvent) {
        if (!audioStopEvent.getNewTrack().isPresent()) {
            Bot.getDiscordClient().getConnectedVoiceChannels().clear();
        }
    }
}
