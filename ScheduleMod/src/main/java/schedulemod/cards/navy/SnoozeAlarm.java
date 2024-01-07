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
            1
    );

    private static final int DROWSY = 1;

    public SnoozeAlarm() {
        super(ID, info);
        setMagic(DROWSY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SnoozeAlarmPower(p, p, this.magicNumber, this.upgraded)));
    }

    @Override
    public AbstractCard makeCopy() { return new SnoozeAlarm(); }
}
