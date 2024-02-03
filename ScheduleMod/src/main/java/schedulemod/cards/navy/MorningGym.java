package schedulemod.cards.navy;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Gym;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class MorningGym extends BaseCard {
    public static final String ID = makeID(MorningGym.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0);

    private static final int SCHEDULE_SLOT = 1;

    public MorningGym() {
        super(ID, info);
        setMagic(SCHEDULE_SLOT);
        this.cardsToPreview = new Gym();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.cardsToPreview.upgrade();
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() {
        return new MorningGym();
    }
}
