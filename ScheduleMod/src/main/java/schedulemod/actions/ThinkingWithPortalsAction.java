package schedulemod.actions;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ThinkingWithPortalsAction extends BetterDiscardPileToHandAction {

    AbstractCard toExclude;
    int numCards;

    public ThinkingWithPortalsAction(int numberOfCards, boolean optional) {
        super(numberOfCards, optional);
        this.numCards = numberOfCards;
    }

    public ThinkingWithPortalsAction(int numberOfCards, int newCost) {
        super(numberOfCards, newCost);
        this.numCards = numberOfCards;
    }

    public ThinkingWithPortalsAction(int numberOfCards) {
        super(numberOfCards);
        this.numCards = numberOfCards;
    }

    public ThinkingWithPortalsAction(AbstractCard toExclude, int numberOfCards) {
        super(numberOfCards);
        this.numCards = numberOfCards;
        this.toExclude = toExclude;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        boolean cardsChosen = false;
        if (duration == startDuration && toExclude != null) {
            p.discardPile.removeCard(toExclude);
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardsChosen = true;
        }
        super.update();
        if (toExclude != null && cardsChosen && !toExclude.exhaust) {
            //p.discardPile.addToTop(toExclude);
        }
    }

}
