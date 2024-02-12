package schedulemod.cards.choiceCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.navy.BaseCard;
import schedulemod.util.CardStats;

public class HyperfloorShop extends BaseCard {
    public static final String ID = makeID(HyperfloorShop.class.getSimpleName());

    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            0);

    public HyperfloorShop() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new HyperfloorShop();
    }
}
