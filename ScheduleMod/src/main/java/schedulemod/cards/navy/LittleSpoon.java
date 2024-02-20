package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.DrowsyPower;
import schedulemod.util.CardStats;

public class LittleSpoon extends BaseCard {
    public static final String ID = makeID(LittleSpoon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 2;
    private static final int DROWSY = 1;
    private static final int UPGRADE_DROWSY = 1;

    public LittleSpoon() {
        super(ID, info);
        setBlock(BLOCK, UPGRADE_BLOCK);
        setMagic(DROWSY, UPGRADE_DROWSY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(m, p, new DrowsyPower(m, p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new LittleSpoon(); }
}
