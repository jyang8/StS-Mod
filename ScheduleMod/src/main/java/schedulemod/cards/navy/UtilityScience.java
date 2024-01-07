package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.LoseEnergyPower;
import schedulemod.powers.UtilitySciencePower;
import schedulemod.util.CardStats;

public class UtilityScience extends BaseCard {
    public static final String ID = makeID(UtilityScience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    private static final int ENERGY_LOSS = 1;
    private static final int WRINKLER = 1;
    private static final boolean ETHEREAL = true;

    public UtilityScience() {
        super(ID, info);
        setMagic(WRINKLER);
        setEthereal(ETHEREAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new LoseEnergyPower(p, ENERGY_LOSS)));
        addToBot(new ApplyPowerAction(p, p, new UtilitySciencePower(p, WRINKLER)));
        if (upgraded)
            addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UtilityScience();
    }
}
