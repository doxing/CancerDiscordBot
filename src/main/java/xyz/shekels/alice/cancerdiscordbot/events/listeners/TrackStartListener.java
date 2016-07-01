package xyz.shekels.alice.cancerdiscordbot.events.listeners;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.audio.events.TrackStartEvent;
import xyz.shekels.alice.cancerdiscordbot.bot.Bot;

/**
 * @author alice
 * @since 7/1/16
 */
public class TrackStartListener implements IListener<TrackStartEvent> {

    @Override
    public void handle(TrackStartEvent trackStartEvent) {
        Bot.getDiscordClient().changeStatus(Status.game(trackStartEvent.getTrack().getMetadata().get("artist") + ", " + trackStartEvent.getTrack().getMetadata().get("album") + ", " + trackStartEvent.getTrack().getMetadata().get("name")));
    }
}
