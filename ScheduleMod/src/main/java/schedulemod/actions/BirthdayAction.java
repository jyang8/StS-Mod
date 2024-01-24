package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import schedulemod.cards.tempCards.BirthdaySong;
import schedulemod.orbs.ScheduleOrb;

public class BirthdayAction extends AbstractGameAction {
    private AbstractCard card;

    public BirthdayAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public void update() {
        this.card.baseDamage += this.amount;
        this.card.applyPowers();
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof BirthdaySong) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof BirthdaySong) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof BirthdaySong) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof ScheduleOrb && ((ScheduleOrb) o).eventCard instanceof BirthdaySong) {
                ((ScheduleOrb) o).eventCard.baseDamage += this.amount;
                ((ScheduleOrb) o).eventCard.applyPowers();
            }
        }
        this.isDone = true;
    }
}
