package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.util.CardStats;

public class SubwayNap extends BaseCard {
    public static final String ID = makeID(SubwayNap.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int FATIGUE = 5;
    private static final int UPGRADE_FATIGUE = 3;
    private static final int NUM_CARDS_DRAW = 1;

    public SubwayNap() {
        super(ID, info);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, NUM_CARDS_DRAW), NUM_CARDS_DRAW));
    }

    @Override
    public AbstractCard makeCopy() { return new SubwayNap(); }
}
