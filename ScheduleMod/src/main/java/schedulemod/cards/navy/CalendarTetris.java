package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.CalendarTetrisPower;
import schedulemod.util.CardStats;

public class CalendarTetris extends BaseCard {
    public static final String ID = makeID(CalendarTetris.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int NUM_CARDS_DRAW = 1;
    private static final boolean INNATE = false;
    private static final boolean UPGRADE_INNATE = true;

    public CalendarTetris() {
        super(ID, info);
        setMagic(NUM_CARDS_DRAW);
        setInnate(INNATE, UPGRADE_INNATE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new CalendarTetrisPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new CalendarTetris(); }
}
