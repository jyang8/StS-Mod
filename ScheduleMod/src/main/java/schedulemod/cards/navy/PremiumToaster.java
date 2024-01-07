package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.tempCards.Bread;
import schedulemod.character.Entropy;
import schedulemod.powers.PremiumToasterPower;
import schedulemod.util.CardStats;

public class PremiumToaster extends BaseCard {
    public static final String ID = makeID(PremiumToaster.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int FATIGUE_BUFF = 2;
    private static final int UPGRADE_FATIGUE_BUFF = 2;

    public PremiumToaster() {
        super(ID, info);
        this.cardsToPreview = new Bread();
        setMagic(FATIGUE_BUFF, UPGRADE_FATIGUE_BUFF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PremiumToasterPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new PremiumToaster(); }
}
