package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Pasta;
import schedulemod.character.Entropy;
import schedulemod.powers.PastaInvisPower;
import schedulemod.util.CardStats;

public class SundayForma extends BaseCard {
    public static final String ID = makeID(SundayForma.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int SCHEDULE_SLOT = 7;
    private static final int UPGRADE_COST = 0;

    public SundayForma() {
        super(ID, info);
        this.setCostUpgrade(UPGRADE_COST);
        this.cardsToPreview = new Pasta();
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScheduleEventCard(this.cardsToPreview, SCHEDULE_SLOT));
        addToBot(new ApplyPowerAction(p, p, new PastaInvisPower(p, p)));
    }

    @Override
    public AbstractCard makeCopy() { return new SundayForma(); }
}
