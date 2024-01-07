package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Nap;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class PowerNap extends BaseCard {
    public static final String ID = makeID(PowerNap.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1);

    private static final int BLOCK = 3;
    private static final int SCHEDULE_SLOT = 3;
    private static final int UPGRADE_SCHEDULE_SLOT = 5;

    public PowerNap() {
        super(ID, info);
        setBlock(BLOCK);
        setMagic(SCHEDULE_SLOT);
        this.cardsToPreview = new Nap();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
        if (upgraded) {
            addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), UPGRADE_SCHEDULE_SLOT));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PowerNap();
    }
}
