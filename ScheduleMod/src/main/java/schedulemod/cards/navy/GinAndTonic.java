package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Drunkus;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class GinAndTonic extends BaseCard {
    public static final String ID = makeID(GinAndTonic.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1);

    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;
    private static final int SCHEDULE_SLOT = 4;

    public GinAndTonic() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setBlock(BLOCK, UPGRADE_BLOCK);
        setMagic(SCHEDULE_SLOT);
        this.cardsToPreview = new Drunkus();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new GinAndTonic();
    }
}
