package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.MFUltimate;
import schedulemod.character.Entropy;
import schedulemod.powers.MFUltimateInvisPower;
import schedulemod.util.CardStats;

public class WinterLoLChamp extends BaseCard {
    public static final String ID = makeID(WinterLoLChamp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2);

    private static final int SCHEDULE_SLOT = 7;

    public WinterLoLChamp() {
        super(ID, info);
        setMagic(SCHEDULE_SLOT);
        this.cardsToPreview = new MFUltimate();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
        addToBot(new ApplyPowerAction(p, p, new MFUltimateInvisPower(p)));
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
        return new WinterLoLChamp();
    }
}
