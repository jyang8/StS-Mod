package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.DateFormPower;
import schedulemod.util.CardStats;

public class DateForm extends BaseCard {
    public static final String ID = makeID(DateForm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    private static final int STACK_AMOUNT = 1;
    private static final boolean ETHEREAL = true;
    private static final boolean UPGRADE_ETHEREAL = false;

    public DateForm() {
        super(ID, info);
        setMagic(STACK_AMOUNT);
        setEthereal(ETHEREAL, UPGRADE_ETHEREAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DateFormPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new DateForm(); }
}
