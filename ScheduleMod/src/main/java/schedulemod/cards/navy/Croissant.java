package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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

    private static final int NUM_CARDS_DRAW = 2;
    private static final int UPGRADE_CARDS_DRAW = 1;

    public Croissant() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setMagic(NUM_CARDS_DRAW, UPGRADE_CARDS_DRAW);
        this.cardsToPreview = new Bread();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new Bread()));
        addToBot(new ApplyPowerAction(p, p, new CroissantPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new Croissant(); }
}
