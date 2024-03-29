package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.SnoozeAlarmPower;
import schedulemod.util.CardStats;

public class SnoozeAlarm extends BaseCard {
    public static final String ID = makeID(SnoozeAlarm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int UPGRADE_COST = 1;

    public SnoozeAlarm() {
        super(ID, info);
        setCostUpgrade(UPGRADE_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SnoozeAlarmPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { return new SnoozeAlarm(); }
}
