package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.navy.BaseCard;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class Bread extends BaseCard {
    public static final String ID = makeID(Bread.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );
    private static final int FATIGUE = 6;
    private static final int UPGRADE_FATIGUE = 4;
    private static final boolean EXHAUST = true;
    private static final boolean RETAIN = true;

    public Bread() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        tags.add(Entropy.Enums.BREAD);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
        setExhaust(EXHAUST);
        setSelfRetain(RETAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { return new Bread(); }
}
