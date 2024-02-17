package schedulemod.cards.deprecated;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.LeftoversAction;
import schedulemod.cards.navy.BaseCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Leftovers extends BaseCard {
    public static final String ID = makeID(Leftovers.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int NUM_CARDS_DRAW = 2;
    private static final int UPGRADE_COST = 0;

    public Leftovers() {
        super(ID, info);
        setMagic(NUM_CARDS_DRAW);
        setCostUpgrade(UPGRADE_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LeftoversAction(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new Leftovers(); }
}
