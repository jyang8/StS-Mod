package schedulemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import schedulemod.cards.EventCard;
import schedulemod.patches.EventsPlayedPatch.EventsPlayedThisTurnField;

public class DailyRemindersAction extends AbstractGameAction {
    private AbstractCard card;
    private static final int SCHEDULE_SLOT = 7;

    public DailyRemindersAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {

        ArrayList<EventCard> eventsPlayed = EventsPlayedThisTurnField.eventsPlayedThisTurn
                .get(AbstractDungeon.actionManager);
        ArrayList<EventCard> eventsToSchedule = new ArrayList<EventCard>();
        if (eventsPlayed.size() > 0) {
            for (EventCard event : eventsPlayed) {
                if (event.triggeringCard == card) {
                    eventsToSchedule.add(event);
                    event.triggeringCard = null;
                }
            }

            int offset = eventsToSchedule.size() - 1;
            for (EventCard event : eventsToSchedule) {
                addToBot(new ScheduleEventCard(event.makeStatEquivalentCopy(), SCHEDULE_SLOT - offset));
                offset--;
            }
        }
        this.isDone = true;
    }
}
