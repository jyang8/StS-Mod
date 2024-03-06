package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.AutomationSciencePower;
import schedulemod.util.CardStats;

public class AutomationScience extends BaseCard {
    public static final String ID = makeID(AutomationScience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int PUNCTUAL = 2;
    private static final int UPGRADE_PUNCTUAL = 1;

    public AutomationScience() {
        super(ID, info);
        setMagic(PUNCTUAL, UPGRADE_PUNCTUAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AutomationSciencePower((AbstractCreature)p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new AutomationScience(); }
}
