package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.PutInScheduleAction;
import schedulemod.cards.tempCards.Repossess;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class SharedKitchen extends BaseCard {
    public static final String ID = makeID(SharedKitchen.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0);

    private static final int SCHEDULE_SLOT = 5;
    private static final int NUM_CARDS_DRAW = 1;

    public SharedKitchen() {
        super(ID, info);
        setMagic(NUM_CARDS_DRAW);
        this.cardsToPreview = new Repossess();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PutInScheduleAction(p, p, this.isInAutoplay, SCHEDULE_SLOT));
        if (upgraded) {
            addToBot(new DrawCardAction(this.magicNumber));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() {
        return new SharedKitchen();
    }
}
