package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.navy.BaseCard;
import schedulemod.util.CardStats;

public class SteamClub extends BaseCard {
    public static final String ID = makeID(SteamClub.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0);

    private static final int NUM_CARDS_DRAW = 2;
    private static final int UPGRADE_NUM_CARDS_DRAW = 1;

    public SteamClub() {
        super(ID, info);
        setExhaust(true);
        setSelfRetain(true);
        setMagic(NUM_CARDS_DRAW, UPGRADE_NUM_CARDS_DRAW);
    } 

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SteamClub();
    }

}
