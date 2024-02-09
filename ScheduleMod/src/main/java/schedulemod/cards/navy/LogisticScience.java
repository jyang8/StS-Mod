package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.SweetDreamsPower;
import schedulemod.util.CardStats;

public class LogisticScience extends BaseCard {
    public static final String ID = makeID(LogisticScience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int TEMP_STATS = 1;
    private static final int UPGRADE_TEMP_STATS = 1;

    public LogisticScience() {
        super(ID, info);
        setMagic(TEMP_STATS, UPGRADE_TEMP_STATS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new LogisticSciencePower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new LogisticScience(); }
}
