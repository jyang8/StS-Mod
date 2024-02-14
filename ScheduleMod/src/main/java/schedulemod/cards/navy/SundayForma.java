package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Pasta;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.util.CardStats;

public class SundayForma extends BaseCard {
    public static final String ID = makeID(SundayForma.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int SCHEDULE_SLOT = 7;
    private static final int FATIGUE = 8;
    private static final int UPGRADE_FATIGUE = 2;

    public SundayForma() {
        super(ID, info);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
        this.cardsToPreview = new Pasta();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (!this.upgraded) {
            this.cardsToPreview.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber), this.magicNumber));
        addToBot(new ScheduleEventCard(this.cardsToPreview, SCHEDULE_SLOT));
    }

    @Override
    public AbstractCard makeCopy() { return new SundayForma(); }
}
