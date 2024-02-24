package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class ThinkingWithPortalsAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int numCards;

    public ThinkingWithPortalsAction(AbstractPlayer p, int numCards) {
        this.p = p;
        this.numCards = numCards;
    }
    public void update() {
        int handSize = p.hand.size();
        addToBot(new DiscardAction(p, p, numCards, false));
        addToBot(new BetterDiscardPileToHandAction(numCards < handSize ? numCards : handSize));
        this.isDone = true;
    }
}
