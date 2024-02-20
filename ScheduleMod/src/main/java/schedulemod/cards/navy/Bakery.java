package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.tempCards.Bread;
import schedulemod.character.Entropy;
import schedulemod.powers.BakeryPower;
import schedulemod.util.CardStats;

public class Bakery extends BaseCard {
    public static final String ID = makeID(Bakery.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int NUM_BREAD = 1;
    private static final boolean INNATE = false;
    private static final boolean UPGRADE_INNATE = true;

    public Bakery() {
        super(ID, info);
        setMagic(NUM_BREAD);
        setInnate(INNATE, UPGRADE_INNATE);
        this.cardsToPreview = new Bread();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BakeryPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new Bakery(); }
}
