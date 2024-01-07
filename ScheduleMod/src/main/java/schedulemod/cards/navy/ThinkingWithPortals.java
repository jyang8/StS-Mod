package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class ThinkingWithPortals extends BaseCard {
    public static final String ID = makeID(ThinkingWithPortals.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    private static final int NUM_CARDS_DISCARD = 2;
    private static final int NUM_CARDS_DRAW = 2;
    private static final boolean EXHAUST = true;
    private static final boolean UPGRADE_EXHAUST = false;

    public ThinkingWithPortals() {
        super(ID, info);
        setMagic(NUM_CARDS_DRAW);
        setExhaust(EXHAUST, UPGRADE_EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p, p, NUM_CARDS_DISCARD, false));
        addToBot(new BetterDiscardPileToHandAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new ThinkingWithPortals(); }
}
