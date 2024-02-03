package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.FortyEightHourDayPower;
import schedulemod.util.CardStats;

public class FortyEightHourDay extends BaseCard {
    public static final String ID = makeID(FortyEightHourDay.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    private static final boolean EXHAUST = true;
    private static final boolean UPGRADE_EXHAUST = false;

    public FortyEightHourDay() {
        super(ID, info);
        setExhaust(EXHAUST, UPGRADE_EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FortyEightHourDayPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() { return new FortyEightHourDay(); }
}
