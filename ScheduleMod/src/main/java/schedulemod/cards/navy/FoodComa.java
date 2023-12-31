package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
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

    private static final int FATIGUE = 4;
    private static final int UPGRADE_FATIGUE = 2;
    private static final int ENERGY_GAIN = 1;

    public FoodComa() {
        super(ID, info);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber)));
        if (p.hasPower(makeID("Satiety"))) {
            addToBot(new GainEnergyAction(ENERGY_GAIN));
            addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new FoodComa(); }
}
