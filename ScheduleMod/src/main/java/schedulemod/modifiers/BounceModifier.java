package schedulemod.modifiers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import basemod.abstracts.AbstractCardModifier;

public class BounceModifier extends AbstractCardModifier {

    private boolean upgraded;

    public BounceModifier(boolean upgraded) {
        this.upgraded = upgraded;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (card.type != AbstractCard.CardType.POWER) {
            if (upgraded) {
                action.returnToHand = true;
                card.returnToHand = true;
            } else {
                action.reboundCard = true;
            }
        }
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BounceModifier(upgraded);
    }

}
