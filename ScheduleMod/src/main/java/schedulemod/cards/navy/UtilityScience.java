package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.UtilitySciencePower;
import schedulemod.util.CardStats;

public class UtilityScience extends BaseCard {
    public static final String ID = makeID(UtilityScience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int WRINKLER = 1;
    private static final boolean INNATE = false;
    private static final boolean UPGRADE_INNATE = true;

    public UtilityScience() {
        super(ID, info);
        setMagic(WRINKLER);
        setInnate(INNATE, UPGRADE_INNATE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new UtilitySciencePower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UtilityScience();
    }
}
