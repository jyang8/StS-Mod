package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
            1
    );

    private static final int SATIETY_DECREASE = 1;
    private static final int UPGRADE_SATIETY_DECREASE = 1;
    private static final int NUM_CARDS_DRAW = 3;
    private static final int UPGRADE_NUM_CARDS_DRAW = 4;

    public Hunger() {
        super(ID, info);
        setMagic(SATIETY_DECREASE, UPGRADE_SATIETY_DECREASE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, -this.magicNumber)));
        addToBot(new DrawCardAction(upgraded ? UPGRADE_NUM_CARDS_DRAW : NUM_CARDS_DRAW));
    }

    @Override
    public AbstractCard makeCopy() { return new Hunger(); }
}
