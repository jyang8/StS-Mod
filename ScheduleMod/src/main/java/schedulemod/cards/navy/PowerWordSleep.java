package schedulemod.cards.navy;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.SleepAction;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class PowerWordSleep extends BaseCard {
    public static final String ID = makeID(PowerWordSleep.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    private static final boolean EXHAUST = true;
    private static int UPGRADE_COST = 1;

    public PowerWordSleep() {
        super(ID, info);
        setExhaust(EXHAUST);
        setCostUpgrade(UPGRADE_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SleepAction(m, p));
    }

    @Override
    public AbstractCard makeCopy() { return new PowerWordSleep(); }
}
