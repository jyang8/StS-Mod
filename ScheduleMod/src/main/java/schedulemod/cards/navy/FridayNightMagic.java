package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Draft;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class FridayNightMagic extends BaseCard {
    public static final String ID = makeID(FridayNightMagic.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    private static final int NUM_CARDS_DRAW = 2;
    private static final int SCHEDULE_SLOT = 5;
    private static final int ADDITIONAL_SCHEDULE_SLOT = 6;

    public FridayNightMagic() {
        super(ID, info);
        setMagic(NUM_CARDS_DRAW);
        this.cardsToPreview = new Draft();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
        if (upgraded)
            addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), ADDITIONAL_SCHEDULE_SLOT));
    }

    @Override
    public AbstractCard makeCopy() { return new FridayNightMagic(); }
}
