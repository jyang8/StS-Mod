package schedulemod.cards.navy;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.AbsurdNap;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class TableAtMission extends BaseCard {
    public static final String ID = makeID(TableAtMission.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int SCHEDULE_SLOT = 5;

    public TableAtMission() {
        super(ID, info);
        this.cardsToPreview = new AbsurdNap();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!upgraded) {
            this.cardsToPreview.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScheduleEventCard(this.cardsToPreview, SCHEDULE_SLOT));
    }

    @Override
    public AbstractCard makeCopy() { return new TableAtMission(); }
}
