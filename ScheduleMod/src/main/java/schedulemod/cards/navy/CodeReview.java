package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.PutOnDeckUpgradeAction;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.util.CardStats;

public class CodeReview extends BaseCard {
    public static final String ID = makeID(CodeReview.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int FATIGUE = 11;
    private static final int UPGRADE_FATIGUE = 2;

    public CodeReview() {
        super(ID, info);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded)
            addToBot(new PutOnDeckAction(p, p, 1, false));
        else
            addToBot(new PutOnDeckUpgradeAction(p, p, 1, false));
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new CodeReview(); }
}
