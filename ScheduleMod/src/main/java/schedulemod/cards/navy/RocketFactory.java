package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.tempCards.Launch;
import schedulemod.character.Entropy;
import schedulemod.powers.RocketFactoryPower;
import schedulemod.util.CardStats;

public class RocketFactory extends BaseCard {
    public static final String ID = makeID(RocketFactory.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int DEBUFFS_REQUIRED = 3;
    private static final boolean INNATE = false;
    private static final boolean UPGRADE_INNATE = true;

    public RocketFactory() {
        super(ID, info);
        setInnate(INNATE, UPGRADE_INNATE);
        setMagic(DEBUFFS_REQUIRED);
        this.cardsToPreview = new Launch();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RocketFactoryPower(p)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new RocketFactory();
    }
}
