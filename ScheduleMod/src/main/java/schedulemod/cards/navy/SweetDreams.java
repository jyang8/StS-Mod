package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.SweetDreamsPower;
import schedulemod.util.CardStats;

public class SweetDreams extends BaseCard {
    public static final String ID = makeID(SweetDreams.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int NUM_CARDS_DRAW = 2;
    private static final int UPGRADE_NUM_CARDS_DRAW = 1;

    public SweetDreams() {
        super(ID, info);
        setMagic(NUM_CARDS_DRAW, UPGRADE_NUM_CARDS_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SweetDreamsPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new SweetDreams(); }
}
