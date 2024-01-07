package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import schedulemod.character.Entropy;

public class LeftoversAction extends AbstractGameAction {
    public LeftoversAction(AbstractCreature source, int amount) {
        this.source = source;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c: AbstractDungeon.player.hand.group) {
                if (c.hasTag(Entropy.Enums.FOOD)) {
                    addToTop(new DiscardSpecificCardAction(c));
                    addToTop(new DrawCardAction(amount));
                }
            }
            this.isDone = true;
        }
    }
}
