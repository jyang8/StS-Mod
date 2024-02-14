package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;

import schedulemod.character.Entropy;
import schedulemod.powers.HokkaidoUniBuffetPower;
import schedulemod.util.CardStats;

public class HokkaidoUniBuffet extends BaseCard {
    public static final String ID = makeID(HokkaidoUniBuffet.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    private static final int NUM_CARDS_DRAW = 2;
    private static final int UPGRADE_NUM_CARDS_DRAW = 1;

    public HokkaidoUniBuffet() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setMagic(NUM_CARDS_DRAW, UPGRADE_NUM_CARDS_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new HokkaidoUniBuffetPower(p, 0)));
        addToBot(new ApplyPowerAction(p, p, new NoDrawPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { return new HokkaidoUniBuffet(); }
}
