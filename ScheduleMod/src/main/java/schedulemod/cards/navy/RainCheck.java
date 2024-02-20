package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.RainCheckAction;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class RainCheck extends BaseCard {
    public static final String ID = makeID(RainCheck.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1);

    private static final int BLOCK = 11;
    private static final int UPGRADE_BLOCK = 3;

    public RainCheck() {
        super(ID, info);
        setBlock(BLOCK, UPGRADE_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new RainCheckAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new RainCheck();
    }
}
