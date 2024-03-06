package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.SpaceSciencePower;
import schedulemod.util.CardStats;

public class SpaceScience extends BaseCard {
    public static final String ID = makeID(SpaceScience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    private static final int ADDITIONAL_WRINKLER = 1;
    private static final int UPGRADE_ADDITIONAL_WRINKLER = 1;

    public SpaceScience() {
        super(ID, info);
        setMagic(ADDITIONAL_WRINKLER, UPGRADE_ADDITIONAL_WRINKLER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SpaceSciencePower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpaceScience();
    }
}
