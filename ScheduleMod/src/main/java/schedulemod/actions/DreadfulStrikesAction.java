package schedulemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.cards.EventCard;
import schedulemod.cards.navy.DND;
import schedulemod.cards.tempCards.DreadfulStrikes;
import schedulemod.character.Entropy;
import schedulemod.orbs.ScheduleOrb;

public class DreadfulStrikesAction extends AbstractGameAction {
    private AbstractCard card;

    public DreadfulStrikesAction(AbstractCard card, int amount) {
        this.card = card;
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

            this.card.baseDamage += this.amount;
            this.card.applyPowers();
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof DND) {
                    c.cardsToPreview.magicNumber += this.amount;
                    c.applyPowers();
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof DND) {
                    c.cardsToPreview.magicNumber += this.amount;
                    c.applyPowers();
                }
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof DND) {
                    c.cardsToPreview.magicNumber += this.amount;
                    c.applyPowers();
                }
            }
            ArrayList<AbstractOrb> orbs = p.orbs;
            for (AbstractOrb orb : orbs) {
                if (!(orb instanceof ScheduleOrb)) {
                    continue;
                }
                EventCard eventCard = ((ScheduleOrb) orb).eventCard;
                if (eventCard instanceof DreadfulStrikes) {
                    eventCard.magicNumber += amount;
                    eventCard.isMagicNumberModified = eventCard.magicNumber != eventCard.baseMagicNumber;
                    eventCard.initializeDescription();
                }
            }
            this.isDone = true;
        }
    }
}
