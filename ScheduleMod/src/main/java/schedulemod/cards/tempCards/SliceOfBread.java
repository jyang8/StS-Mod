package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.navy.BaseCard;
import schedulemod.util.CardStats;

public class SliceOfBread extends BaseCard {
    public static final String ID = makeID(SliceOfBread.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );
    private static final int BLOCK = 8;
    private static final boolean EXHAUST = true;
    private static final boolean RETAIN = true;

    public SliceOfBread() {
        super(ID, info);
        setBlock(BLOCK);
        setExhaust(EXHAUST);
        setSelfRetain(RETAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() { return new SliceOfBread(); }
}
