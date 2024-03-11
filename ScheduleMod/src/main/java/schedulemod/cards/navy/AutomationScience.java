package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.tempCards.Optimize;
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

    private static final int THRESHOLD = 2;
    private static final int UPGRADE_THRESHOLD = 2;

    public AutomationScience() {
        super(ID, info);
        setMagic(THRESHOLD, UPGRADE_THRESHOLD);
        this.cardsToPreview = new Optimize();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AutomationSciencePower((AbstractCreature)p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new AutomationScience(); }
}
