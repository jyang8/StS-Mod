package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.DeepSleepPower;
import schedulemod.util.CardStats;

public class DeepSleep extends BaseCard {
    public static final String ID = makeID(DeepSleep.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int WRINKLER = 2;
    private static final int UPGRADE_WRINKLER = 1;

    public DeepSleep() {
        super(ID, info);
        setMagic(WRINKLER, UPGRADE_WRINKLER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DeepSleepPower(p, p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new DeepSleep(); }
}
