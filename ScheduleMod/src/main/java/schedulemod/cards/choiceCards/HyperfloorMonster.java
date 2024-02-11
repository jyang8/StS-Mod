package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.navy.BaseCard;
import schedulemod.util.CardStats;

public class HyperfloorMonster extends BaseCard {
    public static final String ID = makeID(HyperfloorMonster.class.getSimpleName());

    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            0);

    public HyperfloorMonster() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new HyperfloorMonster();
    }
}
