package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
            0
    );
    private static final boolean INNATE = false;
    private static final boolean UPGRADE_INNATE = true;

    public HokkaidoUniBuffet() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setInnate(INNATE, UPGRADE_INNATE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO: Draw 1 card for each card in Schedule.
        addToBot(new ApplyPowerAction(p, p, new HokkaidoUniBuffetPower(p, 0)));
    }

    @Override
    public AbstractCard makeCopy() { return new HokkaidoUniBuffet(); }
}
