package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Oversleep;
import schedulemod.character.Entropy;
import schedulemod.powers.PunctualPower;
import schedulemod.util.CardStats;

public class Overcommit extends BaseCard {
    public static final String ID = makeID(Overcommit.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int PUNCTUAL = 7;
    private static final int UPGRADE_PUNCTUAL = 3;
    private static final int SCHEDULE_SLOT = 4;

    public Overcommit() {
        super(ID, info);
        setMagic(PUNCTUAL, UPGRADE_PUNCTUAL);
        this.cardsToPreview = new Oversleep();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PunctualPower(p, p, this.magicNumber)));
        addToBot(new ScheduleEventCard(cardsToPreview, SCHEDULE_SLOT));
    }

    @Override
    public AbstractCard makeCopy() { return new Overcommit(); }
}
