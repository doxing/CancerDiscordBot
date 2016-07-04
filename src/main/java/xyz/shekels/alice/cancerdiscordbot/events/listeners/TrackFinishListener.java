package xyz.shekels.alice.cancerdiscordbot.events.listeners;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;

/**
 * @author alice
 * @since 7/4/16
 */
public class TrackFinishListener implements IListener<TrackFinishEvent>{

    @Override
    public void handle(TrackFinishEvent trackFinishEvent) {
        if (!trackFinishEvent.getNewTrack().isPresent()) {
            Bot.getDiscordClient().getConnectedVoiceChannels().get(0).leave();
            Bot.getDiscordClient().changeStatus(Status.game("ded"));
        }
    }
}
