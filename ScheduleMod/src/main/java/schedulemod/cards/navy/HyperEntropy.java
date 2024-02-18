package schedulemod.cards.navy;



import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.HyperEntropyAction;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class HyperEntropy extends BaseCard {
    public static final String ID = makeID(HyperEntropy.class.getSimpleName());

    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            3
    );
    private static final int UPGRADE_COST = 2;
    private static final boolean EXHAUST = true;

    public HyperEntropy() {
        super(ID, info);
        setExhaust(EXHAUST);
        setCostUpgrade(UPGRADE_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HyperEntropyAction((Entropy)p));
    }

    @Override
    public AbstractCard makeCopy() { return new HyperEntropy(); }
}
