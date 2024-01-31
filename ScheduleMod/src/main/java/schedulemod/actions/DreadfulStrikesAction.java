package schedulemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.cards.EventCard;
import schedulemod.cards.tempCards.DreadfulStrikes;
import schedulemod.character.Entropy;
import schedulemod.orbs.ScheduleOrb;

public class DreadfulStrikesAction extends AbstractGameAction {
    public DreadfulStrikesAction(int amount) {
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            if (!(p instanceof Entropy)) {
                this.isDone = true;
                return;
            }
            ArrayList<AbstractOrb> orbs = p.orbs;
            for (AbstractOrb orb : orbs) {
                if (!(orb instanceof ScheduleOrb)) {
                    continue;
                }
                EventCard eventCard = ((ScheduleOrb)orb).eventCard;
                if(eventCard instanceof DreadfulStrikes) {
                    eventCard.magicNumber += amount;
                    eventCard.isMagicNumberModified = eventCard.magicNumber != eventCard.baseMagicNumber;
                    eventCard.initializeDescription();
                }
            }
            this.isDone = true;
        }
    }
}
