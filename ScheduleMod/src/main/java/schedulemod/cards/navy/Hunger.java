package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.DinnerRoundTwo;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class Hunger extends BaseCard {
    public static final String ID = makeID(Hunger.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1);

    private static final int SATIETY_DECREASE = 1;
    private static final int SCHEDULE_SLOT = 3;
    private static final int BLOCK = 7;
    private static final int UPGRADE_BLOCK = 2;

    public Hunger() {
        super(ID, info);
        setMagic(SATIETY_DECREASE);
        setBlock(BLOCK, UPGRADE_BLOCK);
        this.cardsToPreview = new DinnerRoundTwo();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        if (upgraded && p.hasPower(SatietyPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, -p.getPower(SatietyPower.POWER_ID).amount)));
        } else if (!upgraded) {
            addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, -this.magicNumber)));
        }
        addToBot(new ScheduleEventCard(cardsToPreview, SCHEDULE_SLOT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hunger();
    }
}
