package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.JagerBomb;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class EmergencyRedBull extends BaseCard {
    public static final String ID = makeID(EmergencyRedBull.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int HP_LOSS = 3;
    private static final int SCHEDULE_SLOT = 4;

    public EmergencyRedBull() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        this.cardsToPreview = new JagerBomb();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOSS));
        addToBot(new ScheduleEventCard(cardsToPreview, SCHEDULE_SLOT));
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
        return new EmergencyRedBull();
    }
}
