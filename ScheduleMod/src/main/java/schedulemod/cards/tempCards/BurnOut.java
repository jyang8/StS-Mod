package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class BurnOut extends EventCard {
    public static final String ID = makeID(BurnOut.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0);

    private final static int ENERGY_LOSS = 2;
    private final static int UPGRADE_ENERGY_LOSS = -1;

    public BurnOut() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(ENERGY_LOSS, UPGRADE_ENERGY_LOSS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseEnergyAction(this.magicNumber));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseEnergyAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BurnOut();
    }
}
