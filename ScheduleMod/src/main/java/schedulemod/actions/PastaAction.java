package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.cards.EventCard;
import schedulemod.cards.tempCards.Pasta;
import schedulemod.orbs.ScheduleOrb;
import schedulemod.powers.PastaInvisPower;

public class PastaAction extends AbstractGameAction {
    private static final float STARTING_DURATION = 0.2F;

    public PastaAction() {
        this.duration = STARTING_DURATION;
    }

    @Override
    public void update() {
        if (duration == STARTING_DURATION) {
            AbstractPlayer p = AbstractDungeon.player;
            boolean shouldRemove = true;
            for (AbstractOrb orb : p.orbs) {
                if (orb instanceof ScheduleOrb) {
                    EventCard card = ((ScheduleOrb) orb).eventCard;
                    if (card instanceof Pasta) {
                        shouldRemove = false;
                        card.baseBlock += card.magicNumber;
                        card.initializeDescription();
                    }
                }
                
            }
            if (shouldRemove) {
                addToBot(new RemoveSpecificPowerAction(p, p, PastaInvisPower.POWER_ID));
            }
            this.isDone = true;
        }
    }
}
