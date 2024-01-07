package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import schedulemod.character.Entropy;

public class ExhaustAllNonFoodAction extends AbstractGameAction {
//    private float startingDuration = Settings.ACTION_DUR_FAST;

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!c.hasTag(Entropy.Enums.FOOD))
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }
        this.isDone = true;
    }
}
