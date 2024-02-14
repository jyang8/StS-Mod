package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.tempCards.Bread;
import schedulemod.character.Entropy;
import schedulemod.powers.CroissantPower;
import schedulemod.util.CardStats;

public class Croissant extends BaseCard {
    public static final String ID = makeID(Croissant.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int BELLY_SIZE = 1;
    private static final boolean EXHAUST = true;
    private static final boolean RETAIN = false;
    private static final boolean UPGRADE_RETAIN = true;

    public Croissant() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setMagic(BELLY_SIZE);
        setSelfRetain(RETAIN, UPGRADE_RETAIN);
        setExhaust(EXHAUST);
        this.cardsToPreview = new Bread();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new CroissantPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new Croissant(); }
}
