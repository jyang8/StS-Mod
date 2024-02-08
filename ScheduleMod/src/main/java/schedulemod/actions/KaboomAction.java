package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.cards.tempCards.Cleaning;
import schedulemod.orbs.ScheduleOrb;

public class KaboomAction extends AbstractGameAction {
    private Cleaning card;
    private final int SCHEDULE_SLOT = 1;

    public KaboomAction(Cleaning card) {
        this.card = card;
    }

    public void update() {
        int count = 0;        
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
            AbstractOrb o = AbstractDungeon.player.orbs.get(i);
            if (o instanceof ScheduleOrb) {
                count++;
                addToBot(new RemoveFromScheduleAction(i));
            }
        }
        card.setX(count);
        addToBot(new ScheduleEventCard(card.makeStatEquivalentCopy(), SCHEDULE_SLOT));
        this.isDone = true;
    }
}
