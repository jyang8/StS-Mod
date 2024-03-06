package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Arcade extends EventCard {
    public static final String ID = makeID(Arcade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private final static int BLOCK = 7;
    private final static int UPGRADE_BLOCK = 3;
    private final static int SCHEDULE_SLOT = 7;

    public Arcade() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setBlock(BLOCK, UPGRADE_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ScheduleEventCard(this.makeStatEquivalentCopy(), SCHEDULE_SLOT));
    }

    @Override
    public AbstractCard makeCopy() { return new Arcade(); }
}
