package schedulemod.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.navy.BaseCard;
import schedulemod.character.Entropy;
import schedulemod.powers.MetabolizePower;
import schedulemod.util.CardStats;

public class Metabolize extends BaseCard {
    public static final String ID = makeID(Metabolize.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            2
    );

    private int AMOUNT = 1;
    private int UPGRADE_COST = 1;

    public Metabolize() {
        super(ID, info);
        setMagic(AMOUNT);
        setCostUpgrade(UPGRADE_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MetabolizePower(p, p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new Metabolize(); }
}
