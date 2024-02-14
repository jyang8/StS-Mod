package schedulemod.cards.navy;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Arcade;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class MondayArcade extends BaseCard {
    public static final String ID = makeID(MondayArcade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0);

    private static final int SCHEDULE_SLOT = 1;
    private static final boolean EXHAUST = true;
    private static final boolean UPGRADE_EXHAUST = false;

    public MondayArcade() {
        super(ID, info);
        setMagic(SCHEDULE_SLOT);
        setExhaust(EXHAUST, UPGRADE_EXHAUST);
        this.cardsToPreview = new Arcade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() {
        return new MondayArcade();
    }
}
