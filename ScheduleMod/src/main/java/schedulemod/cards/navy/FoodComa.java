package schedulemod.cards.navy;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.FoodComaAction;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class FoodComa extends BaseCard {
    public static final String ID = makeID(FoodComa.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int FATIGUE = 6;
    private static final int UPGRADE_FATIGUE = 3;

    public FoodComa() {
        super(ID, info);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FoodComaAction(p, m, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new FoodComa(); }
}
