package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.cards.EventCard;
import schedulemod.orbs.ScheduleOrb;

public class QualityTimeAction extends AbstractGameAction {

    public QualityTimeAction() {
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof ScheduleOrb) {
                EventCard c = ((ScheduleOrb)o).eventCard;
                if (!c.upgraded)
                    c.upgrade();
            }
        }
        this.isDone = true;
    }
}
