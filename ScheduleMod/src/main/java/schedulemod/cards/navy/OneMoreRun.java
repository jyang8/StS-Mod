package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.powers.VoidPower;
import schedulemod.util.CardStats;

public class OneMoreRun extends BaseCard {
    public static final String ID = makeID(OneMoreRun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1);

    private static final int FATIGUE = 18;
    private static final int UPGRADE_FATIGUE = 7;
    private static final int ENERGY_LOSS = 1;

    public OneMoreRun() {
        super(ID, info);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new VoidPower(p, ENERGY_LOSS)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new OneMoreRun();
    }
}
